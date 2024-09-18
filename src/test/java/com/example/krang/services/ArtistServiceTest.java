package com.example.krang.services;

import com.example.krang.entities.Artist;
import com.example.krang.exceptions.ResourceNotFoundException;
import com.example.krang.exceptions.UserAlreadyExistsException;
import com.example.krang.repository.ArtistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ArtistServiceTest {

    @InjectMocks
    private ArtistService artistService;

    @Mock
    private ArtistRepository artistRepository;

    private Artist artist1;
    private Artist artist2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        artist1 = new Artist();
        artist1.setId(1L);
        artist1.setName("Test Artist 1");

        artist2 = new Artist();
        artist2.setId(2L);
        artist2.setName("Test Artist 1");
    }


    @Test
    void artistService_createArtist_ThrowsExceptionsWhenNameAlreadyExists () {
        when(artistRepository.existsByName(artist1.getName())).thenReturn(true);

        artistRepository.save(artist1);

        assertThrows(UserAlreadyExistsException.class, () -> {
            artistService.createArtist(artist1);
        });

    }

    @Test
    void artistService_createArtist_SavesArtistWhenNameDoesNotExists() {

        when(artistRepository.existsByName(artist1.getName())).thenReturn(false);
        when(artistRepository.save(artist1)).thenReturn(artist1);

        Artist savedArtist = artistService.createArtist(artist1);

        assertEquals(artist1.getName(), "Test Artist 1");
        assertEquals(artist1, savedArtist);
        verify(artistRepository, times(1)).save(artist1);
    }

    @Test
    void artistService_getAllArtists_ListAllArtists () {

        artistRepository.save(artist1);
        artistRepository.save(artist2);

        List<Artist> expectedArtists = Arrays.asList(artist1, artist2);
        when(artistRepository.findAll()).thenReturn(expectedArtists);


        List<Artist> acutalArtists = artistService.getAllArtists();

        assertEquals(expectedArtists, acutalArtists);
        verify(artistRepository, times(1)).findAll();
    }

    @Test
    void artistService_deleteArtistById_ThrowsExceptionsIfArtistIdDoesNotExists() {
        when(artistRepository.existsById(artist1.getId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            artistService.deleteArtistById(artist1.getId());
        });
    }

    @Test
    void artistService_deleteArtistById_DeletesArtistIfIdExists() {

        when(artistRepository.existsById(artist1.getId())).thenReturn(true);

        artistService.deleteArtistById(artist1.getId());

        verify(artistRepository, times(1)).deleteById(artist1.getId());

    }

    @Test
    void artistService_UpdateArtist_ThrowsExceptionIfArtistIdDoesNotExist() {

        when(artistRepository.existsById(artist1.getId())).thenReturn(false);


        assertThrows(ResourceNotFoundException.class, () -> {
            artistService.updateArtist(artist1.getId(), artist1);
        });
    }

    @Test
    void artistService_UpdateArtist_UpdatesArtistIfIdExists() {

        when(artistRepository.existsById(artist1.getId())).thenReturn(true);

        Artist updatedArtist = new Artist();
        updatedArtist.setName("Updated Artist Name");

        artistService.updateArtist(artist1.getId(), updatedArtist);

        verify(artistRepository, times(1)).save(argThat(artist ->
                artist.getName().equals("Updated Artist Name")));
    }



    @Test
    void artistService_GetArtistByName_ReturnsArtistsIfArtistIdExists() {
        when(artistRepository.findById(artist1.getId())).thenReturn(Optional.of(artist1));

        List<Artist> expectedArtist = artistService.getArtistByName(artist1.getName());

        Assertions.assertThat(expectedArtist).contains(artist1);
    }


    @Test
    void artistService_GetArtistByName_ThrowsExceptionsIfArtistsNameDoesNotExist() {

        String wrongName = "Omar";
        when(artistRepository.findArtistsByName(wrongName)).thenReturn(Collections.emptyList());


        assertThrows(ResourceNotFoundException.class, () -> {
            artistService.getArtistByName(wrongName);
        });
    }


}
