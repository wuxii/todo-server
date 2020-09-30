package com.harmony.todo.handler;

import com.harmony.todo.dingtalk.DingtalkAction;
import com.harmony.todo.dingtalk.DingtalkActionHandler;
import com.harmony.todo.dingtalk.DingtalkRequest;
import com.harmony.todo.domain.User;
import com.harmony.todo.domain.UserAccount;
import com.harmony.todo.dto.FindOrCreateUserRequest;
import com.harmony.todo.service.UserService;
import org.springframework.util.Assert;

public abstract class AbstractActionHandler implements DingtalkActionHandler {

    protected final String action;

    protected AbstractActionHandler(String action) {
        Assert.notNull(action, "action not allow null");
        this.action = action;
    }

    protected abstract UserService getUserService();

    @Override
    public final boolean canHandle(String action) {
        return this.action.equalsIgnoreCase(action);
    }

    protected User getActionUser(DingtalkAction action) {
        DingtalkRequest dingtalkRequest = action.getDingtalkRequest();
        String senderId = dingtalkRequest.getSenderId();
        String nickname = dingtalkRequest.getSenderNick();
        FindOrCreateUserRequest request = new FindOrCreateUserRequest()
                .setUsername(nickname)
                .setNickname(nickname)
                .setAccountType(UserAccount.TYPE_OF_DINGTALK)
                .setAccount(senderId);
        return getUserService().findOrCreateUserByAccount(request);
    }

}
