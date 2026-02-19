package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDataTask1 {
    public static void main(String[] args) {
            ApplicationContext applicationContext =
                    SpringApplication.run(SpringDataTask1.class, args);
        }
    }