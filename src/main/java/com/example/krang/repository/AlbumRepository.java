package com.example.krang.repository;

import com.example.krang.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Long> {
    List<Album> findByArtistId(Long artistId);
}
