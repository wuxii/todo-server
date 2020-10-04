package com.harmony.todo.dingtalk.action;

import com.harmony.todo.dingtalk.Action;
import lombok.Data;
import org.apache.commons.cli.Options;

import java.util.Arrays;
import java.util.List;

@Data
public class ActionOptions implements Action {

    private String action;
    private List<String> alias;
    private Options options;
    private String desc;

    public ActionOptions() {
    }

    public ActionOptions(String action, String desc, Options options, String... alias) {
        this.action = action;
        this.options = options;
        this.desc = desc;
        this.alias = Arrays.asList(alias);
    }

    @Override
    public String getAction() {
        return action;
    }

}
