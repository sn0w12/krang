package com.example.krang.services;

import com.example.krang.entities.User;
<<<<<<< HEAD
import com.example.krang.exceptions.UserAlreadyExistsException;
=======
import com.example.krang.execptions.UserAlreadyExistsException;
import com.example.krang.execptions.ResourceNotFoundException;
>>>>>>> 1bd2d6c76795b1cbcb52461037867d0a073d5138
import com.example.krang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Skapa en ny användare
    public User createUser(User user) {
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new UserAlreadyExistsException("Username or email already taken.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Hämta en användare baserat på id
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
