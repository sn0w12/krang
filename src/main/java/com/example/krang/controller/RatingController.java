package com.example.krang.controller;

import com.example.krang.entities.Rating;
import com.example.krang.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // Endpoint för att betygsätta media
    @PostMapping
    public ResponseEntity<Rating> rateMedia(@RequestParam Long userId, @RequestParam Long mediaId, @RequestParam boolean thumbsUp) {
        Rating rating = ratingService.rateMedia(userId, mediaId, thumbsUp);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }


    // PUT-metod för att uppdatera ett betyg
    @PutMapping
    public ResponseEntity<Rating> updateRating(@RequestParam Long userId, @RequestParam Long mediaId, @RequestParam boolean thumbsUp) {
        Rating updatedRating = ratingService.updateRating(userId, mediaId, thumbsUp);
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    // GET-metod för att hämta alla ratings
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
