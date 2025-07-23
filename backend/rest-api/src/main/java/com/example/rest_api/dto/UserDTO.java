package com.example.rest_api.dto;

public record UserDTO(
    Long id,

    String username,

    String email,

    String password,

    String firstName,

    String lastName,

    Long roleId
) {}