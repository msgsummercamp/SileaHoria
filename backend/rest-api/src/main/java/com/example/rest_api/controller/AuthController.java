package com.example.rest_api.controller;

import com.example.rest_api.model.SignInRequest;
import com.example.rest_api.model.SignInResponse;
import com.example.rest_api.repository.UserRepository;
import com.example.rest_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Authentication", description = "JWT Authentication endpoints")
public class AuthController {
    private UserRepository userRepository;
    private AuthService authService;

    @Operation(
        summary = "Authenticate user and get JWT token",
        description = "Authenticates a user with username and password, returning a JWT token and user role information. " +
                     "The returned JWT token should be included in the Authorization header as 'Bearer {token}' for subsequent API calls."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SignInResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials for authentication",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SignInRequest.class)))
            @RequestBody @Valid SignInRequest signInRequest) {

        String token = authService.login(signInRequest);

        SignInResponse response = new SignInResponse();
        response.setToken(token);

        var user = userRepository.findByUsername(signInRequest.getUsername());
        var userRole = user.getRole();
        response.setRole(userRole);

        return ResponseEntity.ok(response);
    }
}
