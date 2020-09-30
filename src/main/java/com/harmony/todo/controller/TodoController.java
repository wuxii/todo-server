package com.harmony.todo.controller;

import com.harmony.todo.domain.Todo;
import com.harmony.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todos")
    public List<Todo> all() {
        return todoService.findAll(Sort.by(Order.desc("id")));
    }

}
