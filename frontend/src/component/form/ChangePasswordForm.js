import React, {useState} from 'react';
import {connect} from "react-redux";
import {bindActionCreators} from 'redux';

import {useTranslation} from "react-i18next";
import {Button, Form, Row} from 'react-bootstrap';
import {Formik} from "formik";
import {useHistory} from "react-router-dom";
import * as Store from "../../store/ReduxActions";
import PropTypes from "prop-types";

function ChangePasswordForm({actions}) {

    const {t} = useTranslation();
    const history = useHistory();
    const [errors, setErrors] = useState({});
    const [valid, setValid] = useState({});

    const onSubmit = (values, {resetForm}) => {

        actions.changePassword(values)
            .then(res => {
                resetForm({})
                history.push("/");

            })
            .catch(response => {
                setErrors(response)

                // if key doesn't present in errors, then it is valid
                var valid = Object.keys(values).reduce(function (obj, k) {
                    if (!response.hasOwnProperty(k)) obj[k] = values[k];
                    return obj;
                }, {});
                setValid(valid);
            });
    };

    return (
        <Formik
            enableReinitialize
            initialValues={{username: '', password: ''}}
            onSubmit={onSubmit}>
            {({
                  handleChange,
                  handleSubmit,
                  values,
                  touched
              }) => (

                <Form noValidate onSubmit={handleSubmit}>

                    <h2>{t("changePasswordHeader")}</h2>

                    <Form.Group as={Row} controlId="validationFormik13">
                        <Form.Control
                            type="password"
                            name="password"
                            onChange={(v) => {
                                if (errors && errors.password) {
                                    const {password, ...rest} = errors;
                                    setErrors(rest);
                                }
                                if (valid && valid.password) {
                                    const {password, ...rest} = valid;
                                    setValid(rest);
                                }
                                handleChange(v);
                            }}
                            defaultValue={values.password}
                            isValid={touched.password && valid.confirmPassword}
                            isInvalid={!!errors.password}
                            placeholder={t("Password")}
                        />
                        <Form.Control.Feedback type="invalid">{errors.password}</Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group as={Row} controlId="validationFormik14">
                        <Form.Control
                            type="password"
                            name="confirmPassword"
                            onChange={(v) => {
                                if (errors && errors.confirmPassword) {
                                    const {confirmPassword, ...rest} = errors;
                                    setErrors(rest);
                                }
                                if (valid && valid.confirmPassword) {
                                    const {confirmPassword, ...rest} = valid;
                                    setValid(rest);
                                }
                                handleChange(v);
                            }}
                            defaultValue={values.confirmPassword}
                            isValid={touched.confirmPassword && valid.confirmPassword}
                            isInvalid={!!errors.confirmPassword}
                            placeholder={t("Confirm password")}
                        />
                        <Form.Control.Feedback type="invalid">{errors.confirmPassword}</Form.Control.Feedback>
                    </Form.Group>

                    <Row><Button type="submit">{t('changePasswordButton')}</Button></Row>
                </Form>
            )}
        </Formik>
    );
}

ChangePasswordForm.propTypes = {
    actions: PropTypes.object.isRequired
};

const mapDispatchToProps = dispatch => ({
    actions: bindActionCreators(Store, dispatch)
});
export default connect(null, mapDispatchToProps)(ChangePasswordForm);