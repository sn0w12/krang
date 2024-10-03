package com.example.krang.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.krang.entities.Album;
import com.example.krang.entities.Media;
import com.example.krang.exceptions.ResourceNotFoundException;
import com.example.krang.repository.AlbumRepository;
import com.example.krang.repository.MediaRepository;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private AlbumRepository albumRepository;

    // Hämta all media
    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    // Skapa media med ett album
    public Media createMedia(Media media) {
        Long albumId = media.getAlbum().getId();
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID: " + albumId));
        media.setAlbum(album);
        return mediaRepository.save(media);
    }

    // Hämta media baserat på typ
    public List<Media> getMediaByType(String mediaType) {
        return mediaRepository.findByMediaType(mediaType);
    }

    // Hämta media baserat på artist
    public List<Media> getMediaByArtist(String artistName) {
        return mediaRepository.findByArtist_Name(artistName);
    }

    // Hämta media baserat på ID
    public Media findMediaById(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + id));
    }

}
