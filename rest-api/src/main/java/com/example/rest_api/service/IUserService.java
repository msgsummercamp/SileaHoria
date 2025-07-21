package com.example.rest_api.service;

import com.example.rest_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User createUser(String username, String email, String password, String firstname, String lastname);

    User findById(Long id);

    Page<User> findAll(int pageNumber);

    User updateUser(Long id, String username, String email, String password, String firstname, String lastname);

    void deleteUser(Long id);
}
