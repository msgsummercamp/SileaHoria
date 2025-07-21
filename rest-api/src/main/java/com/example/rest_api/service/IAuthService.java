package com.example.rest_api.service;

import com.example.rest_api.model.SignInRequest;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {
    String login(SignInRequest signInRequest);
}
