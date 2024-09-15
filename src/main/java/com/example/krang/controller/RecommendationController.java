package com.example.krang.controller;

import com.example.krang.entities.Media;
import com.example.krang.entities.User;
import com.example.krang.repository.UserRepository;
import com.example.krang.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Media>> getRecommendations(@PathVariable Long userId) {
        // Hämta aanvändare baserat på IDT
        Optional<User> optionalUser = userRepository.findById(userId);

        // Kontrollera om användaren finns
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();  // En klassisk 404 om den ej existerar
        }

        User user = optionalUser.get();  // Men om den finns så hämtas den här istället,

        // Här hämtar vi  rekommendationer för användaren
        List<Media> recommendations = recommendationService.getTopRecommendations(user);

        return ResponseEntity.ok(recommendations);
    }
}
