package com.dy.questions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestionsApplication {

    public static void main(String[] args) {
        System.out.println("项目开始启动………………");
        SpringApplication.run(QuestionsApplication.class, args);
        System.out.println("项目启动完成………………");
    }

}
