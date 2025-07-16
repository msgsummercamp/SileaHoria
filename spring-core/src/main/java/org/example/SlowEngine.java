package org.example;

import org.springframework.stereotype.Component;

@Component
public class SlowEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Starting slow engine...");
        try {
            Thread.sleep(2000);
            System.out.println("Slow engine started");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Engine start interrupted");
        }
    }
}
