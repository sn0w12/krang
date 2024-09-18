package com.example.krang.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artist", schema = "KRANG")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // En artist kan ha många album
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();

    // En artist kan ha många mediaobjekt
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Media> mediaItems = new ArrayList<>();

    // Getters och Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Media> getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(List<Media> mediaItems) {
        this.mediaItems = mediaItems;
    }
}
