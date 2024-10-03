package com.example.krang.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.krang.entities.Album;
import com.example.krang.entities.Media;
import com.example.krang.repository.AlbumRepository;
import com.example.krang.repository.MediaRepository;

@ExtendWith(MockitoExtension.class)  // Använd MockitoExtension istället för SpringBootTest
public class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private MediaService mediaService;

    private Media media;
    private Album album;

    @BeforeEach
    public void setUp() {
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
        // Simulera ett giltigt album finns i databasen
        when(albumRepository.findById(1L)).thenReturn(java.util.Optional.of(album));

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
    public void testCreateMedia_WithInvalidAlbum() {
        // Simulera ett ogiltigt album
        when(albumRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            mediaService.createMedia(media);
        });

        assertEquals("Invalid album ID: 1", exception.getMessage());
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
    public void testGetMediaByArtist() {
        // Simulera att vi hämtar media baserat på artist
        List<Media> mediaList = Arrays.asList(media);
        when(mediaRepository.findByArtist_Name("Test Artist")).thenReturn(mediaList);

        // Kontrollera att vi kan hämta media baserat på artistnamn
        List<Media> result = mediaService.getMediaByArtist("Test Artist");
        assertEquals(1, result.size());
        assertEquals("Test Media", result.get(0).getTitle());
    }
}
