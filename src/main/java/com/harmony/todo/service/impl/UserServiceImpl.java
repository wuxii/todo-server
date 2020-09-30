package com.harmony.todo.service.impl;

import com.harmony.todo.core.lock.ExecutableLockRegistry;
import com.harmony.todo.core.lock.LockKeys;
import com.harmony.todo.core.service.ServiceSupport;
import com.harmony.todo.domain.User;
import com.harmony.todo.domain.UserAccount;
import com.harmony.todo.dto.FindOrCreateUserRequest;
import com.harmony.todo.repository.UserRepository;
import com.harmony.todo.service.UserAccountService;
import com.harmony.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl extends ServiceSupport<User> implements UserService {

    private final UserRepository userRepository;

    private final UserAccountService userAccountService;

    private final ExecutableLockRegistry lockRegistry;

    @Override
    public User findOrCreateUserByAccount(FindOrCreateUserRequest request) {
        String account = request.getAccount();
        String accountType = request.getAccountType();
        Supplier<User> userFinder = () -> findUserByAccount(account, accountType);
        String lockKey = String.format(LockKeys.PATTERN_OF_USER_CREATING_BY_ACCOUNT, accountType, account);
        return doFindOrCreateUser(lockKey, userCreator(request), userFinder);
    }

    private User findUserByAccount(String account, String accountType) {
        Long userId = userAccountService.findUserIdByAccount(account, accountType);
        return userId != null ? findById(userId).orElse(null) : null;
    }

    private User doFindOrCreateUser(String lockKey, Supplier<User> userCreator, Supplier<User> userFinder) {
        User existsUser = userFinder.get();
        if (existsUser != null) {
            log.info("user already exists just return it. {}", existsUser);
            return existsUser;
        }
        return lockRegistry
                .obtainExecutableLock(lockKey)
                .execute(() -> {
                    User user = userFinder.get();
                    if (user != null) {
                        log.info("double check user, user exists just return it. {}", user);
                        return user;
                    }
                    return userCreator.get();
                });
    }

    private Supplier<User> userCreator(FindOrCreateUserRequest request) {
        return () -> this.doCreateUser(request);
    }

    private User doCreateUser(FindOrCreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setNickname(request.getNickname());
        user.setPassword(UUID.randomUUID().toString());
        user.setPasswordExpiredAt(new Date());
        user.setRemark("通过账号绑定自动创建的用户");
        user = save(user);

        UserAccount account = new UserAccount();
        account.setAccount(request.getAccount());
        account.setType(request.getAccountType());
        account.setStatus(UserAccount.STATUS_OF_PREPARE);
        account.setUserId(user.getId());
        userAccountService.bind(account);
        return user;
    }

}
