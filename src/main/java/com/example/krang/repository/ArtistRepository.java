package com.example.krang.repository;

import com.example.krang.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findArtistsByName (String name);

    boolean existsByName (String name);

}
