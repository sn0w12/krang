package com.example.krang.services;

import com.example.krang.entities.Album;
import com.example.krang.entities.Artist;
import com.example.krang.exceptions.ResourceNotFoundException;
import com.example.krang.exceptions.UserAlreadyExistsException;
import com.example.krang.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    @Autowired
    public ArtistService (ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    public Artist createArtist (Artist artist) {
        if (artistRepository.existsByName(artist.getName())) {
            throw new UserAlreadyExistsException("Artist already exist");
        }
        return artistRepository.save(artist);
    }

    public List<Artist> getAllArtists () {
        return artistRepository.findAll();
    }

    public List<Artist> getArtistByName (String name) {

        if (artistRepository.findArtistsByName(name).isEmpty()) {
            throw new ResourceNotFoundException("No artist with name: " + name  + " exist.");
        }
        return artistRepository.findArtistsByName(name);
    }

    public void deleteArtistById (Long id) {

        Artist artist = artistRepository.findById(id)
                        .orElseThrow( () -> new ResourceNotFoundException("Artist not found with id: " + id));

        artistRepository.delete(artist);
    }

    public Artist updateArtist(Long id, Artist artist) {

        Artist updatedArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));
        updatedArtist.setName(artist.getName());
        updatedArtist.setAlbums(artist.getAlbums());
        updatedArtist.setMediaItems(artist.getMediaItems());

        return artistRepository.save(updatedArtist);
    }
}
