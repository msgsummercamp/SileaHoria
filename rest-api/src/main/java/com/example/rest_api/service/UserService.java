package com.example.rest_api.service;

import com.example.rest_api.exception.NotFoundException;
import com.example.rest_api.model.User;
import com.example.rest_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final int PAGE_SIZE = 4;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String email, String password, String firstname, String lastname) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFirstName(firstname);
        newUser.setLastName(lastname);
        return userRepository.save(newUser);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public Page<User> findAll(int pageNumber) {
        return userRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE));
    }

    public User updateUser(Long id, String username, String email, String password, String firstname, String lastname) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        existingUser.setUsername(username);
        existingUser.setEmail(email);
        existingUser.setPassword(password);
        existingUser.setFirstName(firstname);
        existingUser.setLastName(lastname);

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
