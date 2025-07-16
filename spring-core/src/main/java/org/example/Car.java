package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Car extends Vehicle {
    @Autowired
    public Car(@Qualifier("fastEngine") Engine engine) {
        this.engine = engine;
    }

}
