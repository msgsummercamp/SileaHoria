package com.example.rest_api.service;

import com.example.rest_api.exception.NotFoundException;
import com.example.rest_api.model.Role;
import com.example.rest_api.model.User;
import com.example.rest_api.repository.IRoleRepository;
import com.example.rest_api.repository.IUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private static final int PAGE_SIZE = 4;

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String email, String password, String firstname, String lastname) {
        return createUser(username, email, password, firstname, lastname, 2L);
    }

    public User createUser(String username, String email, String password, String firstname, String lastname, Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found with id: " + roleId));

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setFirstName(firstname);
        newUser.setLastName(lastname);
        newUser.setRole(role);

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
        return updateUser(id, username, email, password, firstname, lastname, null);
    }

    public User updateUser(Long id, String username, String email, String password, String firstname, String lastname, Long roleId) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        existingUser.setUsername(username);
        existingUser.setEmail(email);

        if (password != null && !password.startsWith("$2a$")) {
            existingUser.setPassword(passwordEncoder.encode(password));
        } else if (password != null) {
            existingUser.setPassword(password);
        }

        existingUser.setFirstName(firstname);
        existingUser.setLastName(lastname);

        if (roleId != null) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new NotFoundException("Role not found with id: " + roleId));
            existingUser.setRole(role);
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
