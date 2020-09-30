package com.harmony.todo.dingtalk;

public interface DingtalkActionHandler {

    DingtalkResponse handle(DingtalkAction action);

    boolean canHandle(String action);

}
