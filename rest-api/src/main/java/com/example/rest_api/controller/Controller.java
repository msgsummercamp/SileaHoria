package com.example.rest_api.controller;

import com.example.rest_api.dto.UserDTO;
import com.example.rest_api.model.User;
import com.example.rest_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Validated
@RequestMapping("/users")
public class Controller {
    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<Page<User>> usersPaginated(@RequestParam(defaultValue = "0") int page) {
        Page<User> users = userService.findAll(page);

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);

        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody @Valid UserDTO user) {
        User createdUser = userService.createUser(user.username(), user.email(), user.password(), user.firstName(), user.lastName());

        return handleCreatedResource(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody @Valid UserDTO user) {
        User updatedUser = userService.updateUser(id, user.username(), user.email(), user.password(), user.firstName(), user.lastName());

        return handleCreatedResource(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdate(@PathVariable long id, @RequestBody UserDTO user) {
        User existingUser;

        existingUser = userService.findById(id);

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

        User updatedUser = userService.updateUser(id, existingUser.getUsername(), existingUser.getEmail(),
                existingUser.getPassword(), existingUser.getFirstName(),
                existingUser.getLastName());


        return handleCreatedResource(updatedUser);
    }

    private ResponseEntity<User> handleCreatedResource(User resource) {
        if (resource == null) {
            return ResponseEntity.badRequest().build();
        } else {
            URI uri = java.net.URI.create("/users/" + resource.getId());
            return ResponseEntity.created(uri).body(resource);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }


}
