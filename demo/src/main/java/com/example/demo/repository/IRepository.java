package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.List;

public interface IRepository {
    List<User> getUsers();
}
