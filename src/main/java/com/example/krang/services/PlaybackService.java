package com.example.krang.services;

import com.example.krang.entities.Playback;
import com.example.krang.entities.User;
import com.example.krang.repository.PlaybackRepository;
import com.example.krang.execptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        User user = userService.findById(userId);
        return playbackRepository.findByUserId(userId);
    }

    // Ny metod för att hämta mest spelade media för en användare
    public List<Object[]> getMostPlayedMediaForUser(Long userId) {
        // Kontrollera att användaren finns
        User user = userService.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return playbackRepository.findMostPlayedMediaByUser(userId);
    }
}
