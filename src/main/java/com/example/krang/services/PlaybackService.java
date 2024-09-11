package com.example.krang.services;

import com.example.krang.entities.Playback;
import com.example.krang.entities.User;
import com.example.krang.execptions.ResourceNotFoundException;
import com.example.krang.repository.PlaybackRepository;
import com.example.krang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlaybackService {

    @Autowired
    private PlaybackRepository playbackRepository;

    @Autowired
    private UserRepository userRepository;

    public Playback savePlayback(Long userId, Long mediaId, int duration) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Playback playback = new Playback();
        playback.setUser(user);
        playback.setMediaId(mediaId);
        playback.setDuration(duration);
        playback.setPlaybackTime(LocalDateTime.now());

        return playbackRepository.save(playback);
    }
}

