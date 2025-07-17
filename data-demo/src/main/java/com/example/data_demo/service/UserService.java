package com.example.data_demo.service;

import com.example.data_demo.model.User;
import com.example.data_demo.repository.UserRepository;
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

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User updateUser(Long id, String username, String email, String password, String firstname, String lastname) {
        return userRepository.save(new User(id, username, email, password, firstname, lastname));
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public Long countUsers() {
        return userRepository.countUsers();
    }
}
