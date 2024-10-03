package com.example.krang.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.krang.entities.Rating;
import com.example.krang.entities.User;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    // Hämta en specifik rating baserat på användar-ID och media-ID
    Optional<Rating> findByUserIdAndMediaId(Long userId, Long mediaId);

    // Hämta en lista med ratings baserat på användare och typ av rating (tummen upp eller ner)
    List<Rating> findByUserAndThumbsUp(User user, Boolean thumbsUp);
}
