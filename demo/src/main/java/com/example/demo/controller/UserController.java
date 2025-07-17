package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @GetMapping("/users")
    public List<User> users() {
        return List.of(
                new User(1, "Alice", "alice@gmail.com"),
                new User(2, "Bob", "bob@gmail.com"),
                new User(3, "Charlie", "charlie@gmail.com"));
    }
}
