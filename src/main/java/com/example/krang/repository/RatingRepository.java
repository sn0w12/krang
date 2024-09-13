package com.example.krang.repository;

import com.example.krang.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUserIdAndMediaId(Long userId, Long mediaId);
}
