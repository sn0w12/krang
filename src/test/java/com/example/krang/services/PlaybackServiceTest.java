package com.example.krang.services;

import com.example.krang.entities.Playback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest
public class PlaybackServiceTest {

    @Autowired
    private PlaybackService playbackService;

    @Test
    public void testSavePlayback() {
        Playback playback = playbackService.savePlayback(1L, 100L, 300);
        assertNotNull(playback);
        assertEquals(300, playback.getDuration());
    }
}
