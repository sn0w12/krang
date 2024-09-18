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

    // Test för att skapa användare framgångsrikt
    @Test
    public void testCreateUser_Success() {
        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("TestUser", createdUser.getUsername());
        assertEquals("encryptedPassword", createdUser.getPassword());  // Verifiera krypterat lösenord
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Test för att hantera när användare redan finns
    @Test
    public void testCreateUser_UserAlreadyExists() {
        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(user);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    // Test för att hämta användare baserat på ID
    @Test
    public void testFindById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals("TestUser", foundUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    // Test för att hantera när användaren inte finns
    @Test
    public void testFindById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(1L);
        });

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateUser_EmailAlreadyExists() {
        when(userRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(user);
        });
    }

    // Test för lösenordskryptering
    @Test
    public void testCreateUser_PasswordEncryption() {
        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encryptedPassword");

        userService.createUser(user);

        verify(passwordEncoder, times(1)).encode("password123");
    }





}
