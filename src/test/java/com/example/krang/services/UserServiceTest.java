package com.example.krang.services;

import com.example.krang.entities.User;
import com.example.krang.exceptions.ResourceNotFoundException;
import com.example.krang.exceptions.UserAlreadyExistsException;
import com.example.krang.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("TestUser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    public void testCreateUser_Success() {
        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("TestUser", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(user);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testFindById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals("TestUser", foundUser.getUsername());
        assertEquals("test@example.com", foundUser.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(1L);
        });

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateUser_Success() {
        User updatedUser = new User();
        updatedUser.setUsername("UpdatedUser");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPassword("newPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("encryptedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("UpdatedUser", result.getUsername());
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setUsername("UpdatedUser");
        updatedUser.setEmail("updated@example.com");

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(1L, updatedUser);
        });

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testDeleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });

        verify(userRepository, never()).delete(any(User.class));
    }
}
