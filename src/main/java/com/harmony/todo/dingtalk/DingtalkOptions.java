package com.harmony.todo.dingtalk;

import com.harmony.todo.dingtalk.action.ActionOptions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class DingtalkOptions implements Iterable<ActionOptions> {

    public static DingtalkOptions of(ActionOptions... options) {
        Map<String, ActionOptions> optionMap = new HashMap<>();
        for (ActionOptions option : options) {
            for (String alias : option.getAlias()) {
                optionMap.put(alias, option);
            }
            optionMap.put(option.getAction(), option);
        }
        return new DingtalkOptions(optionMap);
    }

    private final Map<String, ActionOptions> optionMap;

    private DingtalkOptions(Map<String, ActionOptions> optionsMap) {
        this.optionMap = optionsMap;
    }

    public ActionOptions findOptions(String command) {
        return findOptions(e -> command.startsWith(e.getKey()));
    }

    public ActionOptions findOptions(Predicate<Entry<String, ActionOptions>> predicate) {
        return optionMap
                .entrySet()
                .stream()
                .filter(predicate)
                .findFirst()
                .map(Entry::getValue)
                .orElse(null);
    }

    @Override
    public Iterator<ActionOptions> iterator() {
        return optionMap.values().iterator();
    }

}
