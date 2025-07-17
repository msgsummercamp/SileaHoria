package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.model.User;

import java.util.List;

@Repository
public class MemoryRepository implements IRepository {
    private final List<User> users;

    public MemoryRepository() {
        this.users = List.of(
                new User(1, "Alice", "alice@gmail.com"),
                new User(2, "Bob", "bob@gmail.com"),
                new User(3, "Charlie", "charlie@gmail.com"));
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
