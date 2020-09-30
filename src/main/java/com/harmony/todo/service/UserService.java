package com.harmony.todo.service;

import com.harmony.todo.core.service.Service;
import com.harmony.todo.domain.User;
import com.harmony.todo.dto.FindOrCreateUserRequest;

public interface UserService extends Service<User> {

    User findOrCreateUserByAccount(FindOrCreateUserRequest request);

}
