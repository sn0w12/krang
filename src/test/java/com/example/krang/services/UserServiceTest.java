package com.example.krang.services;

import com.example.krang.entities.User;
import com.example.krang.exceptions.UserAlreadyExistsException;
import com.example.krang.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}
