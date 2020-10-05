package com.harmony.todo.config;

import com.harmony.todo.dingtalk.DingtalkActionDispatcher;
import com.harmony.todo.dingtalk.DingtalkActionHandler;
import com.harmony.todo.dingtalk.DingtalkOptions;
import com.harmony.todo.dingtalk.Options;
import com.harmony.todo.dingtalk.action.ActionParser;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DingtalkConfig {

    @Bean
    public DingtalkActionDispatcher dingtalkActionDispatcher(ObjectProvider<DingtalkActionHandler> handlers) {
        return new DingtalkActionDispatcher(handlers);
    }

    @Bean
    public ActionParser actionParser() {
        return new ActionParser(dingtalkOptions());
    }

    @Bean
    public DingtalkOptions dingtalkOptions() {
        return DingtalkOptions.of(
                Options.addAction(),
                Options.deleteAction(),
                Options.listAction(),
                Options.helpAction(),
                Options.doneAction()
        );
    }

}
