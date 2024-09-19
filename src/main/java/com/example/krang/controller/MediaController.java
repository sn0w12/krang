package com.example.krang.controller;

import com.example.krang.entities.Media;
import com.example.krang.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @GetMapping
    public ResponseEntity<List<Media>> getAllMedia() {
        List<Media> mediaList = mediaService.getAllMedia();
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Media> createMedia(@RequestBody Media media) {
        Media createdMedia = mediaService.createMedia(media);
        return new ResponseEntity<>(createdMedia, HttpStatus.CREATED);
    }

    @GetMapping("/type/{mediaType}")
    public ResponseEntity<List<Media>> getMediaByType(@PathVariable String mediaType) {
        List<Media> mediaList = mediaService.getMediaByType(mediaType);
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    @GetMapping("/artist/{artistName}")
    public ResponseEntity<List<Media>> getMediaByArtist(@PathVariable String artistName) {
        List<Media> mediaList = mediaService.getMediaByArtist(artistName);
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    // Hämta media baserat på ID
    @GetMapping("/{id}")
    public ResponseEntity<Media> getMediaById(@PathVariable Long id) {
        Media media = mediaService.findMediaById(id);
        return ResponseEntity.ok(media);
    }
}