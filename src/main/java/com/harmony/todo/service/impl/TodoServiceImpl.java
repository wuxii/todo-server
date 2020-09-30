package com.harmony.todo.service.impl;

import com.harmony.todo.core.lock.ExecutableLockRegistry;
import com.harmony.todo.core.lock.LockKeys;
import com.harmony.todo.core.repository.QueryableRepository;
import com.harmony.todo.core.service.ServiceSupport;
import com.harmony.todo.domain.Todo;
import com.harmony.todo.dto.TodoList;
import com.harmony.todo.dto.TodoListRequest;
import com.harmony.todo.repository.TodoRepository;
import com.harmony.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl extends ServiceSupport<Todo> implements TodoService {

    private final TodoRepository todoRepository;

    private final ExecutableLockRegistry lockRegistry;

    @Override
    public TodoList findTodos(TodoListRequest request) {
        int size = request.getSize();
        if (size <= 0) {
            long total = todoRepository.count(conditionOf(request));
            return new TodoList().setTotal(total);
        }
        Sort sort = request.getSort();
        PageRequest pageRequest = PageRequest.of(0, size, sort);
        Page<Todo> todoPage = todoRepository.findAll(conditionOf(request), pageRequest);
        return new TodoList()
                .setTotal(todoPage.getTotalElements())
                .setList(todoPage.toList());
    }

    @Override
    public long nextShortId(Long userId) {
        String lockKey = String.format(LockKeys.PATTERN_OF_TODO_NEXT_SHORT_ID, userId);
        return lockRegistry
                .obtainExecutableLock(lockKey)
                .execute(() -> findUserMaxTodoShortIdBy(userId));
    }

    private long findUserMaxTodoShortIdBy(Long userId) {
        return todoRepository
                .findOne((Specification<Todo>) (root, query, cb) -> {
                    Path<Long> userIdPath = root.get("userId");
                    Path<Long> shortIdPath = root.get("shortId");
                    Expression<Long> maxShortIdFunction = cb.function("max", Long.class, shortIdPath);
                    query.select((Selection) maxShortIdFunction);
                    return cb.equal(userIdPath, userId);
                })
                .map(Todo::getShortId)
                .orElse(0L);
    }

    private Specification<Todo> conditionOf(TodoListRequest request) {
        Long userId = request.getUserId();
        Date deadline = request.getDeadline();
        String type = request.getType();
        return (Specification<Todo>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Path<Long> userIdPath = root.get("userId");
            predicates.add(cb.equal(userIdPath, userId));
            if (deadline != null) {
                Path<Date> deadlinePath = root.get("deadline");
                predicates.add(cb.lessThan(deadlinePath, deadline));
            }
            if (type != null) {
                Path<Boolean> donePath = root.get("done");
                switch (type) {
                    case TodoListRequest.LIST_TYPE_OF_ALL:
                        break;
                    case TodoListRequest.LIST_TYPE_OF_DONE:
                        predicates.add(cb.equal(donePath, true));
                        break;
                    case TodoListRequest.LIST_TYPE_OF_UNDONE:
                        predicates.add(cb.equal(donePath, false));
                        break;
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    protected QueryableRepository<Todo> getRepository() {
        return todoRepository;
    }

}
