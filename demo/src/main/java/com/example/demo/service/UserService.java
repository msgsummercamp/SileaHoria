package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService {
    private final IRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<List<User>> getUsers() {
        logger.info("Retrieving all users");
        return Optional.ofNullable(repository.getUsers());
    }

    @Override
    public Optional<List<User>> getUserById(Long id) {
        logger.info("Filtering users by id: {}", id);
        return Optional.of(repository.getUsers().stream()
                .filter(user -> user.id() == id)
                .toList());
    }
}
