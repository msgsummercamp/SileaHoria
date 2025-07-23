package org.example;

import org.springframework.stereotype.Component;

@Component
public class FastEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Starting fast engine...");
        System.out.println("Fast engine started");
    }
}
