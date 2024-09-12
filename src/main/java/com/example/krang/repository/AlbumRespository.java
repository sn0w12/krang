package com.example.krang.repository;

import com.example.krang.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRespository  extends JpaRepository<Album, Long> {

}
