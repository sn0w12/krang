package com.example.krang.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.krang.entities.Artist;
import com.example.krang.entities.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByGenreInAndIdNotIn(List<String> genre, List<Long> excludedIds);
    List<Media> findByIdNotIn(List<Long> excludedIds);
    List<Media> findByMediaType(String mediaType);
    List<Media> findByArtist(Artist artist);
    List<Media> findByArtist_Name(String name);
    Optional<Media> findById(Long id);  // Lägg till detta om det inte redan finns
}