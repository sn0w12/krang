package com.example.krang.repository;

import com.example.krang.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByGenreInAndIdNotIn(List<String> genre, List<Long> excludedIds);
    List<Media> findByIdNotIn(List<Long> excludedIds);
    List<Media> findByMediaType(String mediaType);
    List<Media> findByArtist(String artistName);
    List<Media> findByArtistName(String artistName);  // Ny metod för att hämta media baserat på artistens namn
    Optional<Media> findById(Long id);  // Lägg till detta om det inte redan finns
}