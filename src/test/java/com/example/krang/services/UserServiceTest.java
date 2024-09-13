package com.example.krang.services;

import com.example.krang.entities.User;
import com.example.krang.execptions.UserAlreadyExistsException;
import com.example.krang.execptions.ResourceNotFoundException;
import com.example.krang.repository.UserRepository;
import com.example.krang.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    // Testar skapandet av en användare
    @Test
    public void testCreateUser_Success() {
        User user = new User("johndoe", "johndoe@example.com", "securepassword");

        // Mockar att användarnamnet och e-posten inte finns i databasen
        Mockito.when(userRepository.existsByUsernameOrEmail("johndoe", "johndoe@example.com")).thenReturn(false);

        // Mockar lösenordskryptering
        Mockito.when(passwordEncoder.encode("securepassword")).thenReturn("encryptedPassword");

        // Mockar att användaren sparas i databasen
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertEquals("johndoe", createdUser.getUsername());
        assertEquals("encryptedPassword", createdUser.getPassword());
    }

    // Testar undantag för när användarnamnet eller e-posten redan finns
    @Test
    public void testCreateUser_UsernameOrEmailExists() {
        User user = new User("johndoe", "johndoe@example.com", "securepassword");

        // Mockar att användarnamnet eller e-posten redan finns
        Mockito.when(userRepository.existsByUsernameOrEmail("johndoe", "johndoe@example.com")).thenReturn(true);

        // Förväntar att undantaget kastas
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user));
    }

    // Testar att hitta en användare via id
    @Test
    public void testFindById_Success() {
        User user = new User("johndoe", "johndoe@example.com", "securepassword");

        // Mockar att användaren hittas
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User foundUser = userService.findById(1L);
        assertEquals("johndoe", foundUser.getUsername());
    }

    // Testar undantag för när användaren inte hittas
    @Test
    public void testFindById_UserNotFound() {
        // Mockar att användaren inte hittas
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Förväntar att undantaget kastas
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(1L));
    }
}
