package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.IService;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class UserController {
    private final IService service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(IService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> users(@RequestParam(required = false) @Min(1) Long id) {
        logger.info("Received request for users{}", id != null ? " with id: " + id : "");

        return service.getUsers(id);
    }
}
