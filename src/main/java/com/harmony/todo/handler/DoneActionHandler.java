package com.harmony.todo.handler;

import com.harmony.todo.dingtalk.DingtalkAction;
import com.harmony.todo.dingtalk.DingtalkResponse;
import com.harmony.todo.domain.Todo;
import com.harmony.todo.domain.User;
import com.harmony.todo.service.TodoService;
import com.harmony.todo.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.harmony.todo.utils.OptionsUtils.getShortId;

@Component
public class DoneActionHandler extends AbstractActionHandler {

    private final UserService userService;
    private final TodoService todoService;

    protected DoneActionHandler(UserService userService, TodoService todoService) {
        super("done");
        this.userService = userService;
        this.todoService = todoService;
    }

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        User user = getActionUser(action);
        Long shortId = getShortId(action);
        Todo todo = todoService.findTodoByShortId(shortId, user.getId());
        todo.setDone(true);
        todo.setDoneAt(new Date());
        return DingtalkResponse.text("#" + shortId + " done");
    }

    @Override
    protected UserService getUserService() {
        return userService;
    }

}
