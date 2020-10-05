package com.harmony.todo.handler;

import com.harmony.todo.dingtalk.DingtalkAction;
import com.harmony.todo.dingtalk.DingtalkResponse;
import com.harmony.todo.dingtalk.DingtalkResponse.DingtalkResponseActionCard;
import com.harmony.todo.dingtalk.DingtalkResponse.DingtalkResponseActionCardButton;
import com.harmony.todo.domain.Todo;
import com.harmony.todo.domain.User;
import com.harmony.todo.service.TodoService;
import com.harmony.todo.service.UserService;
import com.harmony.todo.utils.DateFormatter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddActionHandler extends AbstractActionHandler {

    private final TodoService todoService;

    private final DateFormatter dateFormatter;

    private final UserService userService;

    public AddActionHandler(UserService userService, TodoService todoService, DateFormatter dateFormatter) {
        super("add");
        this.todoService = todoService;
        this.dateFormatter = dateFormatter;
        this.userService = userService;
    }

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        String title = action.getOptionValue("title", "未命名");
        String message = action.getOptionValue("message", null);
        String deadline = action.getOptionValue("deadline", null);
        User user = getActionUser(action);
        // build it
        Todo todo = new Todo();
        todo.setShortId(todoService.nextShortId(user.getId()));
        todo.setTitle(title);
        todo.setMessage(message);
        todo.setDeadline(dateFormatter.parse(deadline));
        todo.setDone(false);
        todo.setUserId(user.getId());
        Todo savedTodo = todoService.save(todo);
        // build response
        DingtalkResponseActionCard actionCard =
                new DingtalkResponseActionCard()
                        .setTitle("#" + savedTodo.getShortId() + " " + title)
                        .setText(message)
                        .setButtons(generateTodoActionButtons(savedTodo))
                        .setHideAvatar("0")
                        .setBtnOrientation("1");
        return DingtalkResponse
                .actionCard()
                .setActionCard(actionCard);
    }

    private List<DingtalkResponseActionCardButton> generateTodoActionButtons(Todo todo) {
        List<DingtalkResponseActionCardButton> buttons = new ArrayList<>();
        buttons.add(new DingtalkResponseActionCardButton()
                .setTitle("完成")
                .setSingleUrl("https://www.dingtalk.com/"));
        buttons.add(new DingtalkResponseActionCardButton()
                .setTitle("取消")
                .setSingleUrl("https://www.dingtalk.com/"));
        return buttons;
    }

    @Override
    protected UserService getUserService() {
        return userService;
    }

}
