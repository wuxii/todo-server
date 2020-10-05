package com.harmony.todo.handler;

import com.harmony.todo.dingtalk.DingtalkAction;
import com.harmony.todo.dingtalk.DingtalkResponse;
import com.harmony.todo.domain.Todo;
import com.harmony.todo.domain.User;
import com.harmony.todo.service.TodoService;
import com.harmony.todo.service.UserService;
import org.springframework.stereotype.Component;

import static com.harmony.todo.utils.OptionsUtils.getShortId;

@Component
public class DeleteActionHandler extends AbstractActionHandler {

    private final TodoService todoService;
    private final UserService userService;

    public DeleteActionHandler(TodoService todoService, UserService userService) {
        super("delete");
        this.todoService = todoService;
        this.userService = userService;
    }

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        User user = getActionUser(action);
        Long shortId = getShortId(action);

        Todo todo = todoService.findTodoByShortId(shortId, user.getId());
        todo.setDeleted(true);
        todoService.save(todo);

        return DingtalkResponse.text("#" + todo.getShortId() + " deleted");
    }

    @Override
    protected UserService getUserService() {
        return userService;
    }

}
