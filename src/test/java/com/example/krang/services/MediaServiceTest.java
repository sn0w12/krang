package com.example.krang.services;

import com.example.krang.entities.Media;
import com.example.krang.repository.MediaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;

    @InjectMocks
    private MediaService mediaService;

    public MediaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMedia() {
        Media media = new Media();
        media.setId(1L);
        media.setGenres(Arrays.asList("Rock", "Pop"));
        media.setMediaType("music");
        media.setReleaseDate(new Date());
        media.setStreamingUrl("http://example.com/stream");

        when(mediaRepository.save(any(Media.class))).thenReturn(media);

        Media createdMedia = mediaService.createMedia(media);

        assertEquals(media, createdMedia);
    }

    @Test
    public void testGetMediaByType() {
        Media media1 = new Media();
        media1.setId(1L);
        media1.setGenres(Arrays.asList("Rock", "Pop"));
        media1.setMediaType("music");
        media1.setReleaseDate(new Date());
        media1.setStreamingUrl("http://example.com/stream1");

        Media media2 = new Media();
        media2.setId(2L);
        media2.setGenres(Arrays.asList("Ambient", "Psychedelic"));
        media2.setMediaType("music");
        media2.setReleaseDate(new Date());
        media2.setStreamingUrl("http://example.com/stream2");

        List<Media> mediaList = Arrays.asList(media1, media2);

        when(mediaRepository.findByMediaType("music")).thenReturn(mediaList);

        List<Media> result = mediaService.getMediaByType("music");

        assertEquals(mediaList, result);
    }
}