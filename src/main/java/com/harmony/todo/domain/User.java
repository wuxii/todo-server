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
@Table(name = "u_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    public static final String ACCOUNT_LOCKED = "locked";
    public static final String ACCOUNT_UNLOCKED = "unlocked";

    private String username;
    private String nickname;
    private String password;
    private String avatar;
    private String gender;
    private String remark;
    private Date registerAt;
    private Date passwordExpiredAt;
    private String accountStatus;
    private Date accountExpiredAt;

}
