package com.harmony.todo.repository;

import com.harmony.todo.core.repository.QueryableRepository;
import com.harmony.todo.domain.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends QueryableRepository<Todo> {

    @Query("select max(o.shortId) from Todo o where o.userId=:userId")
    Optional<Long> findUserMaxTodoShortIdBy(@Param("userId") Long userId);

}
