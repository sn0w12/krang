package com.example.krang.services;

import com.example.krang.entities.Album;
import com.example.krang.entities.Media;
import com.example.krang.repository.MediaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;

    //@Mock
    //private AlbumService albumService;

    @InjectMocks
    private MediaService mediaService;

    private Media media;
    private Album album;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Skapa ett exempelalbum för testning
        album = new Album();
        album.setId(1L);
        album.setTitle("Test Album");

        // Skapa ett exempelmedia för testning
        media = new Media();
        media.setId(1L);
        media.setTitle("Test Media");
        media.setMediaType("music");
        media.setGenre("Pop");
        media.setReleaseDate(new Date());
        media.setStreamUrl("https://example.com/test");
        media.setAlbum(album);
    }

    @Test
    public void testGetAllMedia() {
        // Simulera att vi hämtar en lista med media
        List<Media> mediaList = Arrays.asList(media);
        when(mediaRepository.findAll()).thenReturn(mediaList);

        // Kontrollera att metoden returnerar korrekt lista
        List<Media> result = mediaService.getAllMedia();
        assertEquals(1, result.size());
        assertEquals("Test Media", result.get(0).getTitle());
    }

    @Test
    public void testCreateMedia() {
        // Simulera att vi sparar ett mediaobjekt
        when(mediaRepository.save(any(Media.class))).thenReturn(media);

        Media createdMedia = mediaService.createMedia(media);

        // Kontrollera att mediaobjektet sparas korrekt
        assertEquals("Test Media", createdMedia.getTitle());
        assertEquals("music", createdMedia.getMediaType());
        assertEquals("Pop", createdMedia.getGenre());
        assertEquals(album, createdMedia.getAlbum());
    }

    @Test
    public void testGetMediaByType() {
        // Simulera att vi hämtar media baserat på dess typ
        List<Media> mediaList = Arrays.asList(media);
        when(mediaRepository.findByMediaType("music")).thenReturn(mediaList);

        // Kontrollera att vi kan hämta media baserat på dess typ
        List<Media> result = mediaService.getMediaByType("music");
        assertEquals(1, result.size());
        assertEquals("Test Media", result.get(0).getTitle());
    }

    @Test
    public void testCreateMedia_WithInvalidAlbum() {
        // Simulera ett ogiltigt album
        media.setAlbum(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            mediaService.createMedia(media);
        });

        assertEquals("Invalid album", exception.getMessage());
    }

    @Test
    public void testGetMediaByArtist() {
        // Simulera att vi hämtar media baserat på artist
        List<Media> mediaList = Arrays.asList(media);
        when(mediaRepository.findByArtist("Test Artist")).thenReturn(mediaList);

        // Kontrollera att vi kan hämta media baserat på artistnamn
        List<Media> result = mediaService.getMediaByArtist("Test Artist");
        assertEquals(1, result.size());
        assertEquals("Test Media", result.get(0).getTitle());
    }
}
