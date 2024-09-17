package com.example.krang.services;

import com.example.krang.entities.Album;
import com.example.krang.entities.Media;
import com.example.krang.repository.AlbumRespository;
import com.example.krang.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private AlbumRespository albumRepository;

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media createMedia(Media media) {
        /*
         * Long albumId = media.getAlbum().getId();
         * Album album = albumRepository.findById(albumId)
         * .orElseThrow(() -> new IllegalArgumentException("Invalid album ID: " +
         * albumId));
         * media.setAlbum(album);
         */
        return mediaRepository.save(media);
    }

    public List<Media> getMediaByType(String mediaType) {
        return mediaRepository.findByMediaType(mediaType);
    }

    public List<Media> getMediaByArtist(String artistName) {
        return new ArrayList<>();
        // return mediaRepository.findByArtistName(artistName);
    }
}