package com.example.krang.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "album", schema = "KRANG")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private int releaseYear;

    // Relation till Artist (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    // Relation till Media (One-to-Many)
    @OneToMany(mappedBy = "album")
    private List<Media> mediaList;

    // Getters och Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Media> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }
}
