package com.harmony.todo.dingtalk;

import com.harmony.todo.dingtalk.action.ActionCommand;
import lombok.Data;

@Data
public class DingtalkAction implements Action {

    private DingtalkRequest dingtalkRequest;
    private ActionCommand command;

    public String getAction() {
        return command.getAction();
    }

    public String getOptionValue(String name) {
        return command.getOptionValue(name);
    }

    public String getOptionValue(String name, String defaultValue) {
        return command.getOptionValue(name, defaultValue);
    }

}
