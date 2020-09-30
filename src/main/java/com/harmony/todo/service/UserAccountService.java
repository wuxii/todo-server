package com.harmony.todo.service;

import com.harmony.todo.core.service.Service;
import com.harmony.todo.domain.UserAccount;

public interface UserAccountService extends Service<UserAccount> {

    Long findUserIdByAccount(String account, String type);

    UserAccount bind(UserAccount account);

}
