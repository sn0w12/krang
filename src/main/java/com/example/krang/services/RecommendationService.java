package com.example.krang.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.krang.entities.Media;
import com.example.krang.entities.PlayHistory;
import com.example.krang.entities.User;
import com.example.krang.repository.MediaRepository;
import com.example.krang.repository.PlayHistoryRepository;
import com.example.krang.repository.RatingRepository;

@Service
public class RecommendationService {

    @Autowired
    private PlayHistoryRepository playHistoryRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Metod för att hämta topp 10 rekommendationer för en användare.
     */
    public List<Media> getTopRecommendations(User user) {

        // Hämta användarens  speeelhistorik
        List<PlayHistory> history = playHistoryRepository.findByUser(user);

        // Samla in de mest spelade gernerena
        Map<String, Long> genreCount = history.stream()
                .collect(Collectors.groupingBy(h -> h.getMedia().getGenre(), Collectors.counting()));

        // Här sorteras och hämtas top 3
        List<String> top3Genres = genreCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Filtrera bort redan spelade media
        List<Long> playedMediaIds = history.stream()
                .map(h -> h.getMedia().getId())
                .toList();

        // Hämta media som användaren har gett tumme ner
        List<Long> dislikedMediaIds = ratingRepository.findByUserAndThumbsUp(user, false)
                .stream()
                .map(r -> r.getMedia().getId())
                .toList();

        // Kombinera spelade och ogillade medier att filtrera bort
        List<Long> excludedMediaIds = new ArrayList<>();
        excludedMediaIds.addAll(playedMediaIds);
        excludedMediaIds.addAll(dislikedMediaIds);

        // Hämta 80% media från användarens topp 3 genrer
        List<Media> recommendedFromTopGenres = mediaRepository.findByGenreInAndIdNotIn(top3Genres, excludedMediaIds)
                .stream()
                .limit(8) // 80% av 10 = 8
                .toList();

        // Hämta 20% media från andra genrer
        List<Media> recommendedFromOtherGenres = mediaRepository.findByIdNotIn(excludedMediaIds)
                .stream()
                .filter(media -> !top3Genres.contains(media.getGenre()))
                .limit(2) // 20% av 10 = 2
                .toList();

        // Kombinera rekommendationerna
        List<Media> recommendations = new ArrayList<>(recommendedFromTopGenres);
        recommendations.addAll(recommendedFromOtherGenres);

        return recommendations;
    }
}