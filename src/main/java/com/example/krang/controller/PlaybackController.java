package com.example.krang.controller;

import com.example.krang.DTO.PlaybackRequest;
import com.example.krang.entities.Playback;
import com.example.krang.services.PlaybackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playbacks")
public class PlaybackController {

    @Autowired
    private PlaybackService playbackService;

    // Endpoint för att spara en uppspelning
    @PostMapping
    public ResponseEntity<Playback> savePlayback(@RequestBody PlaybackRequest request) {
        Playback playback = playbackService.savePlayback(request.getUserId(), request.getMediaId(), request.getDuration());
        return new ResponseEntity<>(playback, HttpStatus.CREATED);
    }

    // Ny endpoint för att hämta alla uppspelningar för en användare
    @GetMapping("/{userId}")
    public ResponseEntity<List<Playback>> getPlaybacksForUser(@PathVariable Long userId) {
        List<Playback> playbacks = playbackService.getPlaybacksForUser(userId);
        return new ResponseEntity<>(playbacks, HttpStatus.OK);
    }
}
