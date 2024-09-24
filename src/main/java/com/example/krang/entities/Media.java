package com.example.krang.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "media", schema = "KRANG")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private String mediaType; // music, pod, video, etc.
    private String artist;
    private Date releaseDate;
    private String streamUrl;

    // Relation till Album (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "album_id")

    private Album album;

    public Media() {}

    public Media(String title, String genre, String mediaType, String artist, Date releaseDate, String streamUrl) {
        this.title = title;
        this.genre = genre;
        this.mediaType = mediaType;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.streamUrl = streamUrl;
    }

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
