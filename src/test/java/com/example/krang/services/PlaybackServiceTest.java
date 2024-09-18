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

        playback = new Playback();
        playback.setUser(user);
        playback.setDuration(300);
    }

    @Test
    public void testSavePlayback_Success() {
        when(userService.findById(1L)).thenReturn(user);
        when(playbackRepository.save(any(Playback.class))).thenReturn(playback);

        Playback result = playbackService.savePlayback(1L, 100L, 300);

        assertNotNull(result);
        assertEquals(300, result.getDuration());
        verify(playbackRepository, times(1)).save(any(Playback.class));
    }

    @Test
    public void testGetPlaybacksForUser() {
        when(playbackRepository.findByUserId(1L)).thenReturn(List.of(playback));

        List<Playback> playbacks = playbackService.getPlaybacksForUser(1L);

        assertNotNull(playbacks);
        assertEquals(1, playbacks.size());
        verify(playbackRepository, times(1)).findByUserId(1L);
    }
}
