package com.example.krang.repository;

import com.example.krang.entities.Rating;
import com.example.krang.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUserAndRating(User user, String rating);
}
