package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.IRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IService {
    private final IRepository repository;

    public UserService(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getUsers() {
        return repository.getUsers();
    }
}
