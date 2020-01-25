package com.metalheart.config;

import com.google.common.collect.ImmutableList;
import com.metalheart.security.AuthenticationAfterRegistrationFilter;
import com.metalheart.security.LogoutHandler;
import com.metalheart.security.OAuth2Registration;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.metalheart.EndPoint.AUTH_SIGN_OUT;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2Registration oAuth2Registration;

    @Autowired
    private AuthenticationAfterRegistrationFilter registrationFilter;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private LogoutHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .addFilterBefore(registrationFilter, AnonymousAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers(
                "/js/**",
                "/css/**",
                "/images/**",
                "/webjars/**").permitAll()
            .antMatchers("/taskmanager/**").authenticated()
            .antMatchers("/auth/signin/**").permitAll()
            .antMatchers(HttpMethod.POST, "/user").permitAll()
            .anyRequest().authenticated()
            .and()
        .oauth2Login()
            .successHandler(oAuth2Registration)
            .and()
        .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher(AUTH_SIGN_OUT))
            .logoutSuccessHandler(logoutHandler)
            .permitAll()
            .and()
        .cors()
            .and()
        .csrf()
            .disable()
        .httpBasic()
            .and()
        .exceptionHandling()
            .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
            .and()
        ;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of(appProperties.getRest().getFrontUrl()));
        configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userService) {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Autowired
    public void initialize(AuthenticationManagerBuilder auth,
                           UserDetailsService userService,
                           DataSource dataSource) throws Exception {

        auth.userDetailsService(userService)
            .passwordEncoder(encoder())
            .and()
            .authenticationProvider(authenticationProvider(userService))
            .jdbcAuthentication()
            .usersByUsernameQuery("SELECT username, password, true FROM \"user\" WHERE username=?")
            .authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM \"user\" WHERE username=?")
            .dataSource(dataSource)
        ;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}