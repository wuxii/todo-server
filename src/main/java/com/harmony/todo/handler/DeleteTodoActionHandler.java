package com.harmony.todo.handler;

import com.harmony.todo.dingtalk.DingtalkAction;
import com.harmony.todo.dingtalk.DingtalkActionHandler;
import com.harmony.todo.dingtalk.DingtalkResponse;
import org.springframework.stereotype.Component;

@Component
public class DeleteTodoActionHandler implements DingtalkActionHandler {

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        return DingtalkResponse.text("delete");
    }

    @Override
    public boolean canHandle(String action) {
        return "delete".equalsIgnoreCase(action);
    }

}
