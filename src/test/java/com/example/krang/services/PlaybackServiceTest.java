package com.example.krang.services;

import com.example.krang.entities.Playback;
import com.example.krang.entities.User;
import com.example.krang.execptions.ResourceNotFoundException;
import com.example.krang.repository.PlaybackRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class PlaybackServiceTest {

    @Autowired
    private PlaybackService playbackService;

    @MockBean
    private PlaybackRepository playbackRepository;

    @MockBean
    private UserService userService;

    // Testar lyckad uppspelning
    @Test
    public void testSavePlayback_Success() {
        User user = new User();
        user.setId(1L);

        // Mockar att användaren hittas
        Mockito.when(userService.findById(1L)).thenReturn(user);

        Playback playback = new Playback();
        playback.setUser(user);
        playback.setMediaId(100L);
        playback.setDuration(300);
        playback.setPlaybackTime(LocalDateTime.now());

        // Mockar att uppspelningen sparas
        Mockito.when(playbackRepository.save(any(Playback.class))).thenReturn(playback);

        Playback result = playbackService.savePlayback(1L, 100L, 300);
        assertEquals(300, result.getDuration());
        assertEquals(user, result.getUser());
    }

    // Testar när användaren inte hittas
    @Test
    public void testSavePlayback_UserNotFound() {
        // Mockar att användaren inte hittas
        Mockito.when(userService.findById(1L)).thenThrow(new ResourceNotFoundException("User not found"));

        // Förväntar att undantaget kastas
        assertThrows(ResourceNotFoundException.class, () -> playbackService.savePlayback(1L, 100L, 300));
    }

    // Testar ogiltiga mediaId och duration
    @Test
    public void testSavePlayback_InvalidInput() {
        User user = new User();
        user.setId(1L);

        // Mockar att användaren hittas
        Mockito.when(userService.findById(1L)).thenReturn(user);

        // Förväntar ett undantag när mediaId är null eller ogiltig
        assertThrows(IllegalArgumentException.class, () -> playbackService.savePlayback(1L, null, 300));
        assertThrows(IllegalArgumentException.class, () -> playbackService.savePlayback(1L, -1L, 300));

        // Förväntar ett undantag när duration är negativ eller noll
        assertThrows(IllegalArgumentException.class, () -> playbackService.savePlayback(1L, 100L, -10));
        assertThrows(IllegalArgumentException.class, () -> playbackService.savePlayback(1L, 100L, 0));
    }

    // Testar att hämta alla uppspelningar för en användare
    @Test
    public void testGetPlaybacksForUser_Success() {
        User user = new User();
        user.setId(1L);

        // Mockar att användaren hittas
        Mockito.when(userService.findById(1L)).thenReturn(user);

        List<Playback> playbacks = new ArrayList<>();
        Playback playback1 = new Playback();
        playback1.setUser(user);
        playback1.setMediaId(100L);
        playback1.setDuration(300);
        playback1.setPlaybackTime(LocalDateTime.now());

        Playback playback2 = new Playback();
        playback2.setUser(user);
        playback2.setMediaId(101L);
        playback2.setDuration(400);
        playback2.setPlaybackTime(LocalDateTime.now());

        playbacks.add(playback1);
        playbacks.add(playback2);

        // Mockar att uppspelningar hämtas för användaren
        Mockito.when(playbackRepository.findByUserId(1L)).thenReturn(playbacks);

        List<Playback> result = playbackService.getPlaybacksForUser(1L);
        assertEquals(2, result.size());
        assertEquals(100L, result.get(0).getMediaId());
        assertEquals(101L, result.get(1).getMediaId());
    }

    // Testar att hämta uppspelningar för en användare som inte har några uppspelningar
    @Test
    public void testGetPlaybacksForUser_EmptyResult() {
        User user = new User();
        user.setId(1L);

        // Mockar att användaren hittas men inga uppspelningar finns
        Mockito.when(userService.findById(1L)).thenReturn(user);
        Mockito.when(playbackRepository.findByUserId(1L)).thenReturn(Collections.emptyList());

        List<Playback> result = playbackService.getPlaybacksForUser(1L);
        assertTrue(result.isEmpty());
    }
}
