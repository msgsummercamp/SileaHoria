package com.example.rest_api.controller;

import com.example.rest_api.model.SignInRequest;
import com.example.rest_api.model.SignInResponse;
import com.example.rest_api.repository.IRoleRepository;
import com.example.rest_api.repository.IUserRepository;
import com.example.rest_api.service.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest signInRequest) {
        String token = authService.login(signInRequest);

        SignInResponse response = new SignInResponse();
        response.setToken(token);
        var user = userRepository.findByUsername(signInRequest.getUsername());
        var userRole = user.getRole();
        response.setRole(userRole);

        return ResponseEntity.ok(response);
    }
}
