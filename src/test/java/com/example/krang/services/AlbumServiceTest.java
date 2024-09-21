package com.example.krang.services;

import com.example.krang.entities.Album;
import com.example.krang.entities.Artist;
import com.example.krang.entities.Media;
import com.example.krang.exceptions.ResourceNotFoundException;
import com.example.krang.repository.AlbumRepository;
import com.example.krang.repository.ArtistRepository;
import com.example.krang.repository.MediaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlbumServiceTest {

    @InjectMocks
    private AlbumService albumService;

    @Mock
    private MediaRepository mediaRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistRepository artistRepository;

    private Album album;
    private Media media;
    private Artist artist;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        album = new Album();
        album.setId(1L);
        album.setTitle("Test Album");

        media = new Media();
        media.setId(100L);
        media.setTitle("Test Media");

        artist = new Artist();
        artist.setId(1L);
        artist.setName("Test Artist");
    }

    @Test
    public void testAddMediaToAlbum_Success() {
        List<Media> mediaList = new ArrayList<>();
        album.setMediaList(mediaList);

        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(mediaRepository.findById(100L)).thenReturn(Optional.of(media));

        Album updatedAlbum = albumService.addMediaToAlbum(1L, 100L, 1);

        assertNotNull(updatedAlbum);
        assertEquals(1, updatedAlbum.getMediaList().size());
        assertEquals("Test Media", updatedAlbum.getMediaList().get(0).getTitle());

        verify(albumRepository, times(1)).findById(1L);
        verify(mediaRepository, times(1)).findById(100L);
        verify(albumRepository, times(1)).save(album);
    }

    @Test
    public void testAddMediaToAlbum_AlbumNotFound() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            albumService.addMediaToAlbum(1L, 100L, 1);
        });

        verify(albumRepository, times(1)).findById(1L);
        verify(mediaRepository, never()).findById(anyLong());
    }

    @Test
    public void testAddMediaToAlbum_MediaNotFound() {
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(mediaRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            albumService.addMediaToAlbum(1L, 100L, 1);
        });

        verify(albumRepository, times(1)).findById(1L);
        verify(mediaRepository, times(1)).findById(100L);
    }

    @Test
    public void testCreateAlbum_Success() {
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(albumRepository.save(any(Album.class))).thenReturn(album);

        Album createdAlbum = albumService.createAlbum("New Album", "Rock", 1L);

        assertNotNull(createdAlbum);
        assertEquals("Test Album", createdAlbum.getTitle());
        assertEquals(artist, createdAlbum.getArtist());

        verify(artistRepository, times(1)).findById(1L);
        verify(albumRepository, times(1)).save(any(Album.class));
    }

    @Test
    public void testCreateAlbum_ArtistNotFound() {
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            albumService.createAlbum("New Album", "Rock", 1L);
        });

        verify(artistRepository, times(1)).findById(1L);
        verify(albumRepository, never()).save(any(Album.class));
    }

    @Test
    public void testGetAllAlbums() {
        List<Album> albums = new ArrayList<>();
        albums.add(album);

        when(albumRepository.findAll()).thenReturn(albums);

        List<Album> result = albumService.getAllAlbums();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Album", result.get(0).getTitle());

        verify(albumRepository, times(1)).findAll();
    }

    @Test
    public void testGetAlbumsByArtist_Success() {
        List<Album> albums = new ArrayList<>();
        albums.add(album);
        artist.setAlbums(albums);

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        List<Album> result = albumService.getAlbumsByArtist(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Album", result.get(0).getTitle());

        verify(artistRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAlbumsByArtist_ArtistNotFound() {
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            albumService.getAlbumsByArtist(1L);
        });

        verify(artistRepository, times(1)).findById(1L);
    }
}
