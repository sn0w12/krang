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
}