package com.harmony.todo.handler;

import com.harmony.todo.dingtalk.DingtalkAction;
import com.harmony.todo.dingtalk.DingtalkActionHandler;
import com.harmony.todo.dingtalk.DingtalkResponse;
import com.harmony.todo.domain.Todo;
import com.harmony.todo.domain.User;
import com.harmony.todo.dto.TodoList;
import com.harmony.todo.dto.TodoListRequest;
import com.harmony.todo.service.TodoService;
import com.harmony.todo.service.UserService;
import com.harmony.todo.utils.DateFormatter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class ListTodoActionHandler extends AbstractActionHandler implements DingtalkActionHandler {

    private final TodoService todoService;

    private final DateFormatter dateFormatter;

    private final UserService userService;

    public ListTodoActionHandler(UserService userService, TodoService todoService, DateFormatter dateFormatter) {
        super("list");
        this.userService = userService;
        this.todoService = todoService;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        User user = getActionUser(action);
        String size = action.getOptionValue("size", "5");
        String type = action.getOptionValue("type", TodoListRequest.LIST_TYPE_OF_UNDONE);
        String deadline = action.getOptionValue("deadline", null);
        TodoListRequest request = new TodoListRequest()
                .setSize(Integer.parseInt(size))
                .setSort(Sort.by(Order.desc("id")))
                .setUserId(user.getId())
                .setType(type)
                .setDeadline(dateFormatter.parse(deadline));
        TodoList todos = todoService.findTodos(request);
        return DingtalkResponse.text(toTodoListText(todos));
    }

    private String toTodoListText(TodoList todos) {
        StringBuilder o = new StringBuilder();
        o.append("TODO List:\n");
        Iterator<Todo> it = todos.iterator();
        for (; it.hasNext(); ) {
            Todo todo = it.next();
            o.append("\t#").append(todo.getShortId()).append(" ").append(todo.getTitle());
            if (it.hasNext()) {
                o.append("\n");
            }
        }
        return o.toString();
    }

    @Override
    protected UserService getUserService() {
        return userService;
    }
}