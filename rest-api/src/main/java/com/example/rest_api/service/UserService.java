package com.example.rest_api.service;

import com.example.rest_api.model.User;
import com.example.rest_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String username, String email, String password, String firstname, String lastname) {
        userRepository.save(new User(username, email, password, firstname, lastname));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, String username, String email, String password, String firstname, String lastname) {
        return userRepository.save(new User(id, username, email, password, firstname, lastname));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
