package com.example.krang.services;

import com.example.krang.entities.Rating;
import com.example.krang.entities.User;
import com.example.krang.exceptions.ResourceNotFoundException;
import com.example.krang.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserService userService;

    // Uppdatera ett betyg
    public Rating updateRating(Long userId, Long mediaId, boolean thumbsUp) {
        Rating rating = ratingRepository.findByUserIdAndMediaId(userId, mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for userId: " + userId + " and mediaId: " + mediaId));

        rating.setThumbsUp(thumbsUp);
        return ratingRepository.save(rating);
    }

    // Skapa eller uppdatera betyg
    public Rating rateMedia(Long userId, Long mediaId, boolean thumbsUp) {
        User user = userService.findById(userId);

        // Kolla om användaren redan har betygsatt media
        Optional<Rating> existingRatingOpt = ratingRepository.findByUserIdAndMediaId(userId, mediaId);
        Rating rating;

        if (existingRatingOpt.isPresent()) {
            // Uppdatera befintlig betygsättning
            rating = existingRatingOpt.get();
            rating.setThumbsUp(thumbsUp);
        } else {
            // Skapa en ny betygsättning
            rating = new Rating();
            rating.setUser(user);
            rating.setMediaId(mediaId);
            rating.setThumbsUp(thumbsUp);
        }

        return ratingRepository.save(rating);
    }

    // Hämta alla ratings
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
