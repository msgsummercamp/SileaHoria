package com.example.rest_api.service;

import com.example.rest_api.dto.UserDTO;
import com.example.rest_api.exception.NotFoundException;
import com.example.rest_api.model.Role;
import com.example.rest_api.model.User;
import com.example.rest_api.repository.RoleRepository;
import com.example.rest_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final int PAGE_SIZE = 4;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserDTO userDTO) {
        Role role = roleRepository.findById(userDTO.roleId())
                .orElseThrow(() -> new NotFoundException("Role not found with id: " + userDTO.roleId()));

        User newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setEmail(userDTO.email());
        newUser.setPassword(passwordEncoder.encode(userDTO.password()));
        newUser.setFirstName(userDTO.firstName());
        newUser.setLastName(userDTO.lastName());
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


    public User updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        existingUser.setUsername(userDTO.username());
        existingUser.setEmail(userDTO.email());

        String password = userDTO.password();
        if (password != null){
            if ( passwordIsEncoded(password)) {
                existingUser.setPassword(password);
            } else {
                existingUser.setPassword(passwordEncoder.encode(password));
            }
        }

        existingUser.setFirstName(userDTO.firstName());
        existingUser.setLastName(userDTO.lastName());

        Long roleId = userDTO.roleId();
        if (roleId != null) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new NotFoundException("Role not found with id: " + roleId));
            existingUser.setRole(role);
        }

        return userRepository.save(existingUser);
    }

    boolean passwordIsEncoded(String password) {
        return password.startsWith("$2a$");
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
