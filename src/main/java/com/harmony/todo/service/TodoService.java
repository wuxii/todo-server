package com.harmony.todo.service;

import com.harmony.todo.core.service.Service;
import com.harmony.todo.domain.Todo;
import com.harmony.todo.dto.TodoList;
import com.harmony.todo.dto.TodoListRequest;

public interface TodoService extends Service<Todo> {

    TodoList findTodos(TodoListRequest request);

    long nextShortId(Long userId);

    Todo findTodoByShortId(Long shortId, Long userId);

}
