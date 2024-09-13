package com.example.krang.services;

import com.example.krang.entities.Rating;
import com.example.krang.entities.User;
import com.example.krang.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    @MockBean
    private RatingRepository ratingRepository;

    @MockBean
    private UserService userService;

    @Test
    public void testRateMedia_ThumbsUp() {
        User user = new User();
        user.setId(1L);
        Mockito.when(userService.findById(1L)).thenReturn(user);

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setMediaId(100L);
        rating.setThumbsUp(true);

        Mockito.when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.rateMedia(1L, 100L, true);
        assertTrue(result.isThumbsUp());
        assertEquals(1L, result.getUser().getId());
    }
}
