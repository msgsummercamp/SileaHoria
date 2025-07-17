package com.example.data_demo.service;

import com.example.data_demo.model.User;
import com.example.data_demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private final Long userId = 1L;
    private final String username = "test";
    private final String email = "test@test.com";
    private final String password = "password";
    private final String firstname = "test";
    private final String lastname = "test";

    @BeforeEach
    void setUp() {
        testUser = new User(username, email, password, firstname, lastname);
        testUser.setId(1L);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.createUser(username, email, password, firstname, lastname);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindUserByEmail() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        User user = userService.findUserByEmail(email);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindUserByUsername() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        User user = userService.findUserByUsername(username);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }

    @Test
    void testFindAllUsers() {
        List<User> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser, result.getFirst());
    }

    @Test
    void testUpdateUser() {
        String updatedUsername = "updatedTest";
        User updatedUser = new User(1L, updatedUsername, email, password, firstname, lastname);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User returnedUser = userService.updateUser(1L, updatedUsername, email, password, firstname, lastname);

        assertEquals(updatedUsername, returnedUser.getUsername());
    }

    @Test
    void testDeleteUserSuccess() {
        when(userRepository.existsById(userId)).thenReturn(true);

        assertTrue(userService.deleteUser(userId));
    }

    @Test
    void testDeleteUserFail() {
        Long wrongUserId = 999L;
        when(userRepository.existsById(wrongUserId)).thenReturn(false);

        assertFalse(userService.deleteUser(wrongUserId));
    }

    @Test
    void testFindUserByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findUserByUsername("nonexistent"));
    }
}