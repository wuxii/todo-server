package com.harmony.todo.utils;

import com.harmony.todo.dingtalk.DingtalkAction;
import org.springframework.util.StringUtils;

public class OptionsUtils {

    public static Long getShortId(DingtalkAction action) {
        String shortId = action.getOptionValue("shortId");
        if (!StringUtils.hasText(shortId)) {
            throw new IllegalArgumentException("id param not found");
        }
        try {
            return Long.parseLong(shortId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(shortId + " not valid id");
        }
    }

}
