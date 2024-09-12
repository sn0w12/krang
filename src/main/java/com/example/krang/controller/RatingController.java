package com.example.krang.controller;

import com.example.krang.entities.Rating;
import com.example.krang.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
