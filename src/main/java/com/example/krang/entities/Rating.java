package com.example.krang.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "media_ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation till User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relation till Media
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "media_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

    private Media media;

    @Column(name = "thumbs_up", nullable = false)
    private boolean thumbsUp;  // True för tummen upp, false för tummen ner

    // Standardkonstruktor
    public Rating() {}

    // Konstruktor för att skapa rating med alla fält
    public Rating(User user, Media media, boolean thumbsUp) {
        this.user = user;
        this.media = media;
        this.thumbsUp = thumbsUp;
    }

    // Getters och Setters
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

    public boolean isThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(boolean thumbsUp) {
        this.thumbsUp = thumbsUp;
    }
}
