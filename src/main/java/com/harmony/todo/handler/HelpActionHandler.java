package com.harmony.todo.handler;

import com.harmony.todo.dingtalk.DingtalkAction;
import com.harmony.todo.dingtalk.DingtalkActionHandler;
import com.harmony.todo.dingtalk.DingtalkOptions;
import com.harmony.todo.dingtalk.DingtalkResponse;
import com.harmony.todo.dingtalk.action.ActionOptions;
import lombok.RequiredArgsConstructor;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@RequiredArgsConstructor
@Component
public class HelpActionHandler implements DingtalkActionHandler {

    private final DingtalkOptions options;

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        StringBuilder writer = new StringBuilder();
        options.forEach(e -> this.writeOptionHelp(e, writer));
        return DingtalkResponse.text(writer.toString());
    }

    private void writeOptionHelp(ActionOptions actionOptions, StringBuilder writer) {
        String action = actionOptions.getAction();
        Options options = actionOptions.getOptions();
        StringWriter o = new StringWriter();

        HelpFormatter formatter = new HelpFormatter();
        String actionMessage = action + " " + actionOptions.getDesc();
        formatter.printHelp(new PrintWriter(o), 200, actionMessage, "", options, 10, 10, "");
        writer.append(o.toString()).append("\n");
    }

    @Override
    public boolean canHandle(String action) {
        return "help".equalsIgnoreCase(action);
    }

}
