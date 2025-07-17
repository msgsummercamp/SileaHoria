package com.example.demo.service;

import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import com.example.demo.repository.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IService {
    private final IRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getUsers(Long id) {
        if (id != null) {
            logger.info("Filtering users by id: {}", id);
            return repository.getUsers().stream()
                    .filter(user -> user.id() == id)
                    .toList();
        }
        logger.info("Retrieving all users");
        return repository.getUsers();
    }
}
