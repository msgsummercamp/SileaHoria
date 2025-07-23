package com.example.rest_api.service;

import com.example.rest_api.model.SignInRequest;
import com.example.rest_api.tokenHelpers.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }
}
