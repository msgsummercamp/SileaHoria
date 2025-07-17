package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final IService service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(@Autowired IService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> users() {
        logger.info("received request for users");
        return service.getUsers();
    }
}
