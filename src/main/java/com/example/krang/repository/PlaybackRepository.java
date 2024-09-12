package com.example.krang.repository;

import com.example.krang.entities.Playback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaybackRepository extends JpaRepository<Playback, Long> {

    // Existerande metod för att hitta uppspelningar baserat på användar-ID
    List<Playback> findByUserId(Long userId);

    // Ny metod för att hitta mest spelade media baserat på total uppspelningstid (duration)
    @Query("SELECT p.mediaId, SUM(p.duration) as totalDuration " +
            "FROM Playback p WHERE p.user.id = :userId " +
            "GROUP BY p.mediaId " +
            "ORDER BY totalDuration DESC")
    List<Object[]> findMostPlayedMediaByUser(@Param("userId") Long userId);
}
