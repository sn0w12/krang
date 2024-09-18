package com.example.krang.services;

import com.example.krang.entities.Playback;
import com.example.krang.entities.User;
import com.example.krang.repository.PlaybackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaybackServiceTest {

    @InjectMocks
    private PlaybackService playbackService;

    @Mock
    private PlaybackRepository playbackRepository;

    @Mock
    private UserService userService;

    private User user;
    private Playback playback;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("TestUser");

        playback = new Playback();
        playback.setUser(user);
        playback.setDuration(300);
        playback.setId(100L);
    }

    // Test för att spara en uppspelning
    @Test
    public void testSavePlayback_Success() {
        when(userService.findById(1L)).thenReturn(user);
        when(playbackRepository.save(any(Playback.class))).thenReturn(playback);

        Playback result = playbackService.savePlayback(1L, 100L, 300);

        assertNotNull(result);
        assertEquals(300, result.getDuration());
        assertEquals(user, result.getUser());
        verify(playbackRepository, times(1)).save(any(Playback.class));
    }

    // Test för att hämta alla uppspelningar för en användare
    @Test
    public void testGetPlaybacksForUser() {
        when(playbackRepository.findByUserId(1L)).thenReturn(List.of(playback));

        List<Playback> playbacks = playbackService.getPlaybacksForUser(1L);

        assertNotNull(playbacks);
        assertEquals(1, playbacks.size());
        assertEquals(playback, playbacks.get(0));
        verify(playbackRepository, times(1)).findByUserId(1L);
    }

    // Test för att hantera om ingen uppspelning hittas för en användare
    @Test
    public void testGetPlaybacksForUser_EmptyResult() {
        when(playbackRepository.findByUserId(1L)).thenReturn(List.of());

        List<Playback> playbacks = playbackService.getPlaybacksForUser(1L);

        assertNotNull(playbacks);
        assertTrue(playbacks.isEmpty());
        verify(playbackRepository, times(1)).findByUserId(1L);
    }

    // Test för att hämta uppspelning baserat på ID
    @Test
    public void testGetPlaybackById_Success() {
        when(playbackRepository.findById(100L)).thenReturn(Optional.of(playback));

        Optional<Playback> result = playbackService.getPlaybackById(100L);

        assertTrue(result.isPresent());
        assertEquals(playback, result.get());
        verify(playbackRepository, times(1)).findById(100L);
    }

    // Test för att hantera fall då en uppspelning inte hittas baserat på ID
    @Test
    public void testGetPlaybackById_NotFound() {
        when(playbackRepository.findById(100L)).thenReturn(Optional.empty());

        Optional<Playback> result = playbackService.getPlaybackById(100L);

        assertFalse(result.isPresent());
        verify(playbackRepository, times(1)).findById(100L);
    }
}
