package com.example.krang.entities;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "user_playbacks")
public class Playback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Motsvarar 'id' i tabellen

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Motsvarar 'user_id' i tabellen

    // Här antas att 'Media' är en annan entitet. Om det inte finns kan du använda Long.
    @Column(name = "media_id")
    private Long mediaId;  // Motsvarar 'media_id' i tabellen

    @Column(name = "duration", nullable = false)
    private int duration;  // Motsvarar 'duration' i tabellen

    @Column(name = "playback_time", nullable = false)
    private LocalDateTime playbackTime;  // Motsvarar 'playback_time' i tabellen

    // Getters and setters

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getPlaybackTime() {
        return playbackTime;
    }

    public void setPlaybackTime(LocalDateTime playbackTime) {
        this.playbackTime = playbackTime;
    }
}
