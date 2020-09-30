package com.harmony.todo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FindOrCreateUserRequest {

    private String username;
    private String nickname;

    private String account;
    private String accountType;

}
