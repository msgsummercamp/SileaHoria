package com.example.rest_api.model;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
