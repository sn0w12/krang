package com.example.krang.services;

import com.example.krang.entities.Playback;
import com.example.krang.entities.User;
import com.example.krang.exceptions.ResourceNotFoundException;
import com.example.krang.repository.PlaybackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaybackService {

    @Autowired
    private PlaybackRepository playbackRepository;

    @Autowired
    private UserService userService;

    // Metod för att spara en uppspelning
    public Playback savePlayback(Long userId, Long mediaId, int duration) {
        User user = userService.findById(userId);  // Kontrollera att användaren finns
        if (mediaId == null || mediaId <= 0 || duration <= 0) {
            throw new IllegalArgumentException("Invalid mediaId or duration.");
        }

        Playback playback = new Playback();
        playback.setUser(user);
        playback.setMediaId(mediaId);
        playback.setDuration(duration);
        playback.setPlaybackTime(java.time.LocalDateTime.now());

        return playbackRepository.save(playback);
    }

    // Metod för att hämta alla uppspelningar för en användare
    public List<Playback> getPlaybacksForUser(Long userId) {
        // Kontrollera att användaren finns
        userService.findById(userId);
        return playbackRepository.findByUserId(userId);
    }

    // Metod för att hämta mest spelade media för en användare
    public List<Object[]> getMostPlayedMediaForUser(Long userId) {
        // Kontrollera att användaren finns
        userService.findById(userId);
        return playbackRepository.findMostPlayedMediaByUser(userId);
    }

    // Ny metod för att hämta uppspelning baserat på ID
    public Optional<Playback> getPlaybackById(Long playbackId) {
        return playbackRepository.findById(playbackId);
    }
}
