package com.harmony.todo.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(updatable = false)
    protected Long createdUserId;

    protected String createdUsername;

    @Column(updatable = false)
    protected Date createdAt;

    protected Long updatedUserId;

    protected Date updatedUsername;

}
