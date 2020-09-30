package com.harmony.todo.config;

import com.harmony.todo.utils.DateFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormatterConfig {

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter(DateFormatter.COMMON_DATE_FORMAT_PATTERN_SET);
    }

}
