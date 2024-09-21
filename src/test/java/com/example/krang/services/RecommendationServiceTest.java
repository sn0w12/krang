package com.example.krang.services;

import com.example.krang.entities.Media;
import com.example.krang.entities.PlayHistory;
import com.example.krang.entities.User;
import com.example.krang.repository.MediaRepository;
import com.example.krang.repository.PlayHistoryRepository;
import com.example.krang.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecommendationServiceTest {

    @InjectMocks
    private RecommendationService recommendationService;

    @Mock
    private PlayHistoryRepository playHistoryRepository;

    @Mock
    private MediaRepository mediaRepository;

    @Mock
    private RatingRepository ratingRepository;

    private User user;
    private List<PlayHistory> playHistoryList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Skapa användare
        user = new User();
        user.setId(1L);

        // Skapa mediaobjekt med en genre som sträng istället för lista
        Media media = new Media();
        media.setId(100L);
        media.setTitle("Test Media");
        media.setGenre("Pop");  // Använd genres som en enkel sträng

        // Skapa och tilldela en play history till användaren och media
        PlayHistory playHistory = new PlayHistory();
        playHistory.setUser(user);
        playHistory.setMedia(media);

        // Lägg till play history till listan
        playHistoryList = new ArrayList<>();
        playHistoryList.add(playHistory);
    }


    @Test
    public void testGetTopRecommendations_Success() {
        when(playHistoryRepository.findByUser(user)).thenReturn(playHistoryList);
        when(mediaRepository.findByGenreInAndIdNotIn(anyList(), anyList())).thenReturn(new ArrayList<>());
        when(mediaRepository.findByIdNotIn(anyList())).thenReturn(new ArrayList<>());

        List<Media> recommendations = recommendationService.getTopRecommendations(user);

        assertNotNull(recommendations);
        assertEquals(0, recommendations.size());  // Assuming no media returned for now
        verify(playHistoryRepository, times(1)).findByUser(user);
    }

    @Test
    public void testGetTopRecommendations_WithMedia() {
        when(playHistoryRepository.findByUser(user)).thenReturn(playHistoryList);

        Media recommendedMedia = new Media();
        recommendedMedia.setId(200L);
        recommendedMedia.setTitle("Recommended Media");

        when(mediaRepository.findByGenreInAndIdNotIn(anyList(), anyList())).thenReturn(List.of(recommendedMedia));
        when(mediaRepository.findByIdNotIn(anyList())).thenReturn(new ArrayList<>());

        List<Media> recommendations = recommendationService.getTopRecommendations(user);

        assertNotNull(recommendations);
        assertEquals(1, recommendations.size());
        assertEquals("Recommended Media", recommendations.get(0).getTitle());
    }
}
