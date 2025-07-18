package com.example.rest_api.controller;

import com.example.rest_api.dto.UserDTO;
import com.example.rest_api.model.User;
import com.example.rest_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> all() {
        return userService.findAll();
    }

    @PostMapping("/create")
    public User create(@RequestBody UserDTO user) {
        return userService.createUser(user.username(), user.email(), user.password(), user.firstName(), user.lastName());
    }

    @PutMapping("/update/{id}")
    public User update(@PathVariable long id, @RequestBody UserDTO user) {
        return userService.updateUser(id, user.username(), user.email(), user.password(), user.firstName(), user.lastName());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/partial-update/{id}")
    public User partialUpdate(@PathVariable long id, @RequestBody UserDTO user) {
        User existingUser = userService.findById(id);

        if (user.username() != null) {
            existingUser.setUsername(user.username());
        }
        if (user.email() != null) {
            existingUser.setEmail(user.email());
        }
        if (user.password() != null) {
            existingUser.setPassword(user.password());
        }
        if (user.firstName() != null) {
            existingUser.setFirstName(user.firstName());
        }
        if (user.lastName() != null) {
            existingUser.setLastName(user.lastName());
        }

        return userService.updateUser(id, existingUser.getUsername(), existingUser.getEmail(),
                                      existingUser.getPassword(), existingUser.getFirstName(),
                                      existingUser.getLastName());
    }
}
