package com.example.krang.controller;

import com.example.krang.entities.Artist;
import com.example.krang.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Artist>> getAllArtists() {

        List<Artist> artistList = artistService.getAllArtists();
        return new ResponseEntity<>(artistList, HttpStatus.OK);
    }

    @GetMapping("/{artistName}")
    public ResponseEntity<List<Artist>> getArtistsByName(@PathVariable String artistName) {

        List<Artist> artistByNameList = artistService.getArtistByName(artistName);
        return new ResponseEntity<>(artistByNameList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {

        Artist createdArtist = artistService.createArtist(artist);
        return new ResponseEntity<>(createdArtist, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id,
                                               @RequestBody Artist artist) {

        Artist updatedArtist = artistService.updateArtist(id, artist);
        return new ResponseEntity<>(updatedArtist, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{artistId}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long artistId) {

        artistService.deleteArtistById(artistId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
