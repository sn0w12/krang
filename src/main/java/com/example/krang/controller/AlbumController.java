package com.example.krang.controller;

import com.example.krang.entities.Album;
import com.example.krang.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    // Skapa ett nytt album
    @PostMapping("/create")
    public ResponseEntity<Album> createAlbum(@RequestParam String title, @RequestParam String genre, @RequestParam Long artistId) {
        Album album = albumService.createAlbum(title, genre, artistId);
        return new ResponseEntity<>(album, HttpStatus.CREATED);
    }

    // Lägg till media till ett album
    @PostMapping("/{albumId}/media/{mediaId}")
    public ResponseEntity<Album> addMediaToAlbum(@PathVariable Long albumId, @PathVariable Long mediaId, @RequestParam int order) {
        Album updatedAlbum = albumService.addMediaToAlbum(albumId, mediaId, order);
        return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
    }

    // Ta bort ett album
    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long albumId) {
        albumService.deleteAlbum(albumId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Hämta alla album
    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    // Hämta album för en viss artist
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Album>> getAlbumsByArtist(@PathVariable Long artistId) {
        List<Album> albums = albumService.getAlbumsByArtist(artistId);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
}
