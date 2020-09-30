package com.harmony.todo.repository;

import com.harmony.todo.core.repository.QueryableRepository;
import com.harmony.todo.domain.Todo;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends QueryableRepository<Todo> {
}
