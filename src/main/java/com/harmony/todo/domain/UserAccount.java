package com.harmony.todo.domain;

import com.harmony.todo.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "u_user_account")
@EqualsAndHashCode(callSuper = true)
public class UserAccount extends BaseEntity {

    public static final String TYPE_OF_PHONE = "phone";
    public static final String TYPE_OF_EMAIL = "email";
    public static final String TYPE_OF_WEIXIN = "weixin";
    public static final String TYPE_OF_DINGTALK = "dingtalk";

    public static final String STATUS_OF_PREPARE = "prepare";

    private String type;
    private String status;
    private String account;
    private Long userId;

}
