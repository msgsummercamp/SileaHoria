package org.example;

import org.springframework.stereotype.Component;

@Component
public abstract class Vehicle {
    protected Engine engine;

    public void drive() {
        engine.start();
        System.out.println("Vehicle is driving");
    }
}
