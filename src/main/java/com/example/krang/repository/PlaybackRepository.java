package com.example.krang.repository;

import com.example.krang.entities.Playback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaybackRepository extends JpaRepository<Playback, Long> {
    List<Playback> findByUserId(Long userId);
}
