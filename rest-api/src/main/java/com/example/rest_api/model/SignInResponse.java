package com.example.rest_api.model;

import lombok.Data;

@Data
public class SignInResponse {
    private String token;
    private Role role;
}
