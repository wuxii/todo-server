package com.harmony.todo.dingtalk.action;

import com.harmony.todo.dingtalk.DingtalkOptions;
import org.apache.commons.cli.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionParser {

    private Pattern pattern = Pattern.compile("-\\w* ");
    private DingtalkOptions dingtalkOptions;

    public ActionParser(DingtalkOptions options) {
        this.dingtalkOptions = options;
    }

    public ActionCommand parse(String command) {
        ActionOptions options = dingtalkOptions.findOptions(command);
        if (options == null) {
            throw new IllegalArgumentException("unknown action");
        }
        try {
            String action = options.getAction();
            String commandString = command.substring(action.length()).trim();
            return new ActionCommand(options, toCommands(commandString));
        } catch (ParseException e) {
            throw new IllegalArgumentException("illegal command", e);
        }
    }

    private String[] toCommands(String commandString) {
        List<String> commands = new ArrayList<>();
        Matcher matcher = pattern.matcher(commandString);
        int index = 0;
        while (matcher.find()) {
            if (index > 0) {
                commands.add(commandString.substring(index, matcher.start()).trim());
            }
            commands.add(matcher.group().trim());
            index = matcher.end();
        }
        if (index > 0) {
            commands.add(commandString.substring(index).trim());
        }
        return commands.toArray(new String[0]);
    }

    public void setDingtalkOptions(DingtalkOptions dingtalkOptions) {
        this.dingtalkOptions = dingtalkOptions;
    }

}
