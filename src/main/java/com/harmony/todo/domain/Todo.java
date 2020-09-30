package com.harmony.todo.domain;

import com.harmony.todo.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "t_todo")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Todo extends BaseEntity {

    private long shortId;
    private String title;
    private String message;
    private Date deadline;
    private boolean done;
    private Date doneAt;

    private Long userId;

}
