package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        MessageService messageService = context.getBean(MessageService.class);

        messageService.displayMessage();

        context.close();
    }
}