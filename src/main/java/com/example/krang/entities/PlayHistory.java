package com.example.krang.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "play_history")
public class PlayHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @Column(name = "played_at", nullable = false)
    private LocalDateTime playedAt;

    public PlayHistory() {
    }

    public PlayHistory(User user, Media media) {
        this.user = user;
        this.media = media;
        this.playedAt = LocalDateTime.now();
    }

    // Getters och setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }
}