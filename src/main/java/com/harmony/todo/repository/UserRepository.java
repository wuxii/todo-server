package com.harmony.todo.repository;

import com.harmony.todo.core.repository.QueryableRepository;
import com.harmony.todo.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends QueryableRepository<User> {
}
