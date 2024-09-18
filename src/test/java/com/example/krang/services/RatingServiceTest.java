package com.example.krang.services;

import com.example.krang.entities.Rating;
import com.example.krang.entities.User;
import com.example.krang.entities.Media;
import com.example.krang.repository.RatingRepository;
import com.example.krang.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserService userService;

    @Mock
    private MediaService mediaService;

    private User user;
    private Media media;
    private Rating rating;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("TestUser");

        media = new Media();
        media.setId(100L);
        media.setTitle("Test Media");

        rating = new Rating();
        rating.setUser(user);
        rating.setMedia(media);
        rating.setThumbsUp(true);
    }

    @Test
    public void testRateMedia_NewRating() {
        when(userService.findById(1L)).thenReturn(user);
        when(ratingRepository.findByUserIdAndMediaId(1L, 100L)).thenReturn(Optional.empty());
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.rateMedia(1L, 100L, true);

        assertNotNull(result);
        assertTrue(result.isThumbsUp());
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    public void testRateMedia_UpdateExistingRating() {
        when(userService.findById(1L)).thenReturn(user);
        when(ratingRepository.findByUserIdAndMediaId(1L, 100L)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.rateMedia(1L, 100L, false);

        assertNotNull(result);
        assertFalse(result.isThumbsUp());
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }
}
