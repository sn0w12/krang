package com.example.krang.repository;

import com.example.krang.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByGenreInAndIdNotIn(List<String> genres, List<Long> excludedIds);
    List<Media> findByIdNotIn(List<Long> excludedIds);
}

