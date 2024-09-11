package com.example.krang.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_playbacks")
public class Playback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "media_id", nullable = false)
    private Long mediaId;

    @Column(name = "playback_time", nullable = false)
    private LocalDateTime playbackTime;

    @Column(name = "duration", nullable = false)
    private int duration;

    // Constructors
    public Playback() {
    }

    public Playback(User user, Long mediaId, LocalDateTime playbackTime, int duration) {
        this.user = user;
        this.mediaId = mediaId;
        this.playbackTime = playbackTime;
        this.duration = duration;
    }

    // Getters and Setters
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

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public LocalDateTime getPlaybackTime() {
        return playbackTime;
    }

    public void setPlaybackTime(LocalDateTime playbackTime) {
        this.playbackTime = playbackTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}