package com.example.krang.controller;

import com.example.krang.DTO.PlaybackRequest;
import com.example.krang.entities.Playback;
import com.example.krang.services.PlaybackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playbacks")
public class PlaybackController {

    @Autowired
    private PlaybackService playbackService;

    @PostMapping
    public ResponseEntity<Playback> savePlayback(@RequestBody PlaybackRequest request) {
        Playback playback = playbackService.savePlayback(request.getUserId(), request.getMediaId(), request.getDuration());
        return new ResponseEntity<>(playback, HttpStatus.CREATED);
    }
}
