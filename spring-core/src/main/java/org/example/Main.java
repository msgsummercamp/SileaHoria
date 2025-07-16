package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        MessageService messageService = context.getBean(MessageService.class);

        messageService.displayMessage();

        Vehicle car = context.getBean(Car.class);
        car.drive();

        Vehicle truck = context.getBean(Truck.class);
        truck.drive();

        context.close();
    }
}