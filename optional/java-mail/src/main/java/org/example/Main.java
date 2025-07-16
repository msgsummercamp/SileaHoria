package org.example;

import org.example.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private MailService mailService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter recipient email: ");
        String recipient = scanner.nextLine();

        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();

        System.out.print("Enter email body: ");
        String body = scanner.nextLine();

        mailService.sendMail(recipient, subject, body);

        scanner.close();
    }
}