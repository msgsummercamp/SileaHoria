package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.IRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IRepository repository;

    @InjectMocks
    private UserService userService;

    @Test
    void returnAllUsersFromRepository() {
        List<User> mockUsers = List.of(
                new User(1, "Alice", "alice@gmail.com"),
                new User(2, "Bob", "bob@gmail.com"),
                new User(3, "Charlie", "charlie@gmail.com")
        );
        when(repository.getUsers()).thenReturn(mockUsers);

        List<User> result = userService.getUsers();

        assertEquals(mockUsers, result, "UserService should return all users from the repository");
    }

    @Test
    void returnUserFilteredById() {
        List<User> mockUsers = List.of(
                new User(1, "Alice", "alice@gmail.com"),
                new User(2, "Bob", "bob@gmail.com"),
                new User(3, "Charlie", "charlie@gmail.com")
        );
        when(repository.getUsers()).thenReturn(mockUsers);

        List<User> result = userService.getUserById(2L);

        assertEquals(1, result.size());
        assertEquals("Bob", result.getFirst().username());
        assertEquals("bob@gmail.com", result.getFirst().email());
        assertEquals(2, result.getFirst().id());
    }

    @Test
    void returnEmptyListWhenUserIdNotFound() {
        List<User> mockUsers = List.of(
                new User(1, "Alice", "alice@gmail.com"),
                new User(2, "Bob", "bob@gmail.com")
        );
        when(repository.getUsers()).thenReturn(mockUsers);

        List<User> result = userService.getUserById(999L);

        assertEquals(0, result.size());
    }
}