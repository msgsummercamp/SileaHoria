package com.example.demo.service;


import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface IService {
    Optional<List<User>> getUsers();
    Optional<List<User>> getUserById(Long id);
}
