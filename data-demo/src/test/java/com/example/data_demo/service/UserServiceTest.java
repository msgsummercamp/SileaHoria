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
    void test_createUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        userService.createUser(username, email, password, firstname, lastname);

        assertTrue(userRepository.findById(userId).isPresent());

        assertEquals(testUser, userRepository.findById(userId).get());
    }

    @Test
    void test_findUserByEmail_returnsCorrectUser() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        User user = userService.findUserByEmail(email);

        assertNotNull(user);
        assertEquals(testUser, user);
    }

    @Test
    void test_findUserByUsername_returnsCorrectUser() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        User user = userService.findUserByUsername(username);

        assertNotNull(user);
        assertEquals(testUser, user);
    }

    @Test
    void test_findAllUsers_returnsCorrectList() {
        List<User> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser, result.getFirst());
    }

    @Test
    void test_updateUser_returnsUpdatedUser() {
        String updatedUsername = "updatedTest";
        User updatedUser = new User(1L, updatedUsername, email, password, firstname, lastname);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User returnedUser = userService.updateUser(1L, updatedUsername, email, password, firstname, lastname);

        assertEquals(updatedUsername, returnedUser.getUsername());
    }

    @Test
    void test_deleteUser_doesNotThrowException_whenIdIsValid() {
        when(userRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(userId));
    }

    @Test
    void test_deleteUser_throwsException_whenIdIsInvalid() {
        Long wrongUserId = 999L;
        when(userRepository.existsById(wrongUserId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(wrongUserId));
    }

    @Test
    void test_findUserByUsername_throwsException_whenUserDoesntExist() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findUserByUsername("nonexistent"));
    }

    @Test
    void test_countUsers_returnsCorrectCount() {
        Long expectedCount = 5L;
        when(userRepository.countUsers()).thenReturn(expectedCount);

        Long actualCount = userService.countUsers();

        assertEquals(expectedCount, actualCount);
    }
}