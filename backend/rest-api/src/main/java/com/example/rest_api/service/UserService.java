package com.example.rest_api.service;

import com.example.rest_api.dto.UserDTO;
import com.example.rest_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUser(UserDTO userDTO);

    User findById(Long id);

    Page<User> findAll(int pageNumber);

    User updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);
}
