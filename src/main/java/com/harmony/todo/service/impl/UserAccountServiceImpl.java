package com.harmony.todo.service.impl;

import com.harmony.todo.core.service.ServiceSupport;
import com.harmony.todo.domain.UserAccount;
import com.harmony.todo.repository.UserAccountRepository;
import com.harmony.todo.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Selection;

@RequiredArgsConstructor
@Service
public class UserAccountServiceImpl extends ServiceSupport<UserAccount> implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserAccount bind(UserAccount account) {
        return save(account);
    }

    @Override
    public Long findUserIdByAccount(String account, String type) {
        return userAccountRepository
                .findOne(conditionOf(account, type))
                .map(UserAccount::getUserId)
                .orElse(null);
    }

    private Specification<UserAccount> conditionOf(String account, String type) {
        return (Specification<UserAccount>) (root, query, cb) -> {
            Path<String> accountPath = root.get("account");
            Path<String> typePath = root.get("type");
            query.select((Selection) root.get("userId"));
            return cb.and(cb.equal(accountPath, account), cb.equal(typePath, type));
        };
    }

}
