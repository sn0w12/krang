package com.example.krang.services;

import com.example.krang.entities.Rating;
import com.example.krang.entities.User;
import com.example.krang.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserService userService;

    public Rating rateMedia(Long userId, Long mediaId, boolean thumbsUp) {
        User user = userService.findById(userId);

        // Kolla om användaren redan har betygsatt media
        List<Rating> existingRatings = ratingRepository.findByUserIdAndMediaId(userId, mediaId);
        Rating rating;
        if (existingRatings.isEmpty()) {
            // Skapa en ny betygsättning
            rating = new Rating();
            rating.setUser(user);
            rating.setMediaId(mediaId);
            rating.setThumbsUp(thumbsUp);
        } else {
            // Uppdatera den befintliga betygsättningen
            rating = existingRatings.get(0);
            rating.setThumbsUp(thumbsUp);
        }
        return ratingRepository.save(rating);
    }
}
