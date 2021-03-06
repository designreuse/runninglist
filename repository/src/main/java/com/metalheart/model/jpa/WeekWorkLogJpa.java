package com.metalheart.model.jpa;

import com.metalheart.model.TaskStatus;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "week_work_log")
public class WeekWorkLogJpa implements Serializable {

    @EmbeddedId
    private WeekWorkLogJpaPK id;

    @Type(type = "postgres_enum")
    @Column(name = "status", nullable = false)
    private TaskStatus status;
}
