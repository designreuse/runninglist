package com.metalheart.model.jpa;

import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "task")
public class TaskJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="task_id_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false, name = "user_id")
    private Integer userId;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer priority;

    // todo: check it, add tests
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tag_task",
        joinColumns =        @JoinColumn(name = "task_id",  referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<TagJpa> tags;

    @Column
    private boolean deleted;
}