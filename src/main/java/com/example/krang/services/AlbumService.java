package com.example.krang.services;

import com.example.krang.entities.Album;
import com.example.krang.entities.Artist;
import com.example.krang.entities.Media;
import com.example.krang.exceptions.ResourceNotFoundException;
import com.example.krang.repository.AlbumRepository;
import com.example.krang.repository.ArtistRepository;
import com.example.krang.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    // Lägg till media till ett album
    public Album addMediaToAlbum(Long albumId, Long mediaId, int order) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found with id: " + albumId));
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + mediaId));

        List<Media> mediaList = album.getMediaList();
        if (order > 0 && order <= mediaList.size() + 1) {
            mediaList.add(order - 1, media); // Placera media i önskad ordning
        } else {
            mediaList.add(media); // Om ordning är utanför tillåtet intervall, lägg till sist
        }
        return albumRepository.save(album);
    }

    // Skapa ett nytt album
    public Album createAlbum(String title, String genre, Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + artistId));
        Album album = new Album();
        album.setTitle(title);
        album.setArtist(artist);  // Koppla albumet till artist
        return albumRepository.save(album);
    }

    // Hämta alla album
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    // Hämta alla album för en viss artist
    public List<Album> getAlbumsByArtist(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + artistId));
        return artist.getAlbums();
    }

    // Ta bort ett album
    public void deleteAlbum(Long albumId) {
        if(!albumRepository.existsById(albumId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find and delete album by id " + albumId);
        }
        albumRepository.deleteById(albumId);

    }
}
