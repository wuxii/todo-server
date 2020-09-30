package com.harmony.todo;

import com.harmony.todo.domain.Todo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EntityScan(basePackageClasses = Todo.class)
public class TodoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoServerApplication.class, args);
    }

}
