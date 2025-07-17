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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IRepository repository;

    @InjectMocks
    private UserService userService;

    @Test
    void test_getUsers_returnsAllUsersFromRepository() {
        List<User> mockUsers = List.of(
                new User(1, "Alice", "alice@gmail.com"),
                new User(2, "Bob", "bob@gmail.com"),
                new User(3, "Charlie", "charlie@gmail.com")
        );
        when(repository.getUsers()).thenReturn(mockUsers);

        Optional<List<User>> result = userService.getUsers();

        assertTrue(result.isPresent());

        assertEquals(mockUsers, result.get(), "UserService should return all users from the repository");
    }

    @Test
    void test_getUserById_returnsUserFilteredById_whenUserIsFound() {
        List<User> mockUsers = List.of(
                new User(1, "Alice", "alice@gmail.com"),
                new User(2, "Bob", "bob@gmail.com"),
                new User(3, "Charlie", "charlie@gmail.com")
        );
        when(repository.getUsers()).thenReturn(mockUsers);

        Optional<List<User>> result = userService.getUserById(2L);

        assertTrue(result.isPresent());

        assertEquals(1, result.get().size());
        assertEquals("Bob", result.get().getFirst().username());
        assertEquals("bob@gmail.com", result.get().getFirst().email());
        assertEquals(2, result.get().getFirst().id());
    }

    @Test
    void test_getUserById_returnsEmptyList_whenUserIdNotFound() {
        List<User> mockUsers = List.of(
                new User(1, "Alice", "alice@gmail.com"),
                new User(2, "Bob", "bob@gmail.com")
        );
        when(repository.getUsers()).thenReturn(mockUsers);

        Optional<List<User>> result = userService.getUserById(999L);

        assertTrue(result.isPresent());

        assertEquals(0, result.get().size());
    }
}