package com.harmony.todo.handler;

import com.harmony.todo.dingtalk.DingtalkAction;
import com.harmony.todo.dingtalk.DingtalkResponse;
import com.harmony.todo.domain.Todo;
import com.harmony.todo.domain.User;
import com.harmony.todo.service.TodoService;
import com.harmony.todo.service.UserService;
import org.springframework.util.StringUtils;

import java.util.Date;

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
        String id = action.getOptionValue("id");
        if (!StringUtils.hasText(id)) {
            return DingtalkResponse.text("todo id param not found");
        }
        Long shortId;
        try {
            shortId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return DingtalkResponse.text(id + " not valid todo id");
        }
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
