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

import java.util.List;
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

    // Test för att skapa en ny betygsättning
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

    // Test för att uppdatera en befintlig betygsättning
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

    // Test för att hämta alla betyg
    @Test
    public void testGetAllRatings() {
        List<Rating> ratings = List.of(rating);
        when(ratingRepository.findAll()).thenReturn(ratings);

        List<Rating> result = ratingService.getAllRatings();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(rating, result.get(0));
        verify(ratingRepository, times(1)).findAll();
    }

    // Test för att hantera när betyg inte hittas
    @Test
    public void testUpdateRating_RatingNotFound() {
        when(ratingRepository.findByUserIdAndMediaId(1L, 100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            ratingService.updateRating(1L, 100L, true);
        });

        verify(ratingRepository, never()).save(any(Rating.class));
    }

    // Test för att uppdatera en befintlig betygsättning framgångsrikt
    @Test
    public void testUpdateRating_Success() {
        when(ratingRepository.findByUserIdAndMediaId(1L, 100L)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating updatedRating = ratingService.updateRating(1L, 100L, false);

        assertNotNull(updatedRating);
        assertFalse(updatedRating.isThumbsUp());
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    public void testRateMedia_InvalidUserId() {
        when(userService.findById(999L)).thenThrow(new ResourceNotFoundException("User not found"));
        assertThrows(ResourceNotFoundException.class, () -> {
            ratingService.rateMedia(999L, 100L, true);
        });
    }

    @Test
    public void testUpdateRating() {
        when(userService.findById(1L)).thenReturn(user);
        when(ratingRepository.findByUserIdAndMediaId(1L, 100L)).thenReturn(Optional.of(rating));

        Rating result = ratingService.rateMedia(1L, 100L, false);
        assertFalse(result.isThumbsUp());
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

}
