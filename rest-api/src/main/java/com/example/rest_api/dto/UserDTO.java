package com.example.rest_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserDTO(
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    String username,


    @Email(message = "Email should be valid")
    String email,

    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    String password,

    @Size(max = 50, message = "First name must not exceed 50 characters")
    String firstName,

    @Size(max = 50, message = "Last name must not exceed 50 characters")
    String lastName
) {}