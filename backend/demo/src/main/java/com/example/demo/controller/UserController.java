package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.IService;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
public class UserController {
    private final IService service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(IService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> users(@RequestParam(required = false) @Min(1) Long id) {
        logger.info("Received request for users{}", id != null ? " with id: " + id : "");
        Optional<List<User>> users;

        if (id != null) {
            users = service.getUserById(id);
        } else {
            users = service.getUsers();
        }

        if (users.isEmpty() || users.get().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users.get());
    }
}
