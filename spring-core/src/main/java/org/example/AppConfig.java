package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MessageService messageService() {
        MessageService service = new MessageService();
        service.setMessage("Hello, Spring!");
        return service;
    }
}
