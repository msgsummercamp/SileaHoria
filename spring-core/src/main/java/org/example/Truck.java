package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Truck extends Vehicle {
    @Autowired
    public Truck(@Qualifier("slowEngine") Engine engine) {
        this.engine = engine;
    }
}
