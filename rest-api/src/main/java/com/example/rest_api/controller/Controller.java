package com.example.rest_api.controller;

import com.example.rest_api.dto.UserDTO;
import com.example.rest_api.model.User;
import com.example.rest_api.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Validated
@RequestMapping("/users")
public class Controller {
    private final IUserService userService;

    public Controller(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get paginated list of users", description = "Returns a paginated list of users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<User>> usersPaginated(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") int page) {
        Page<User> users = userService.findAll(page);

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID", description = "Returns a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<User> getUser(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long id) {
        User user = userService.findById(id);

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
            @RequestBody @Valid UserDTO user) {
        User createdUser = userService.createUser(user.username(), user.email(), user.password(), user.firstName(), user.lastName());

        return handleCreatedResource(createdUser);
    }

    @Operation(summary = "Update user", description = "Updates an existing user by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> update(
            @Parameter(description = "User ID", example = "1")
            @PathVariable long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
            @RequestBody @Valid UserDTO user) {
        User updatedUser = userService.updateUser(id, user.username(), user.email(), user.password(), user.firstName(), user.lastName());

        return handleCreatedResource(updatedUser);
    }

    @Operation(summary = "Partially update user", description = "Partially updates an existing user by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> partialUpdate(
            @Parameter(description = "User ID", example = "1")
            @PathVariable long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Partial user details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
            @RequestBody @Valid UserDTO user) {
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

    @Operation(summary = "Delete user", description = "Deletes a user by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @Parameter(description = "User ID", example = "1")
            @PathVariable long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }


}
