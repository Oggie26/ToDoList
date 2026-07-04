package com.example.todollist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodollistApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodollistApplication.class, args);
    }
}
