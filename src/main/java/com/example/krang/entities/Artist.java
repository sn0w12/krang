package com.example.krang.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Artist {

    @Id
    private Long id;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL) // an artist can have many albums
    private List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL) // an artist may have different media
    private List<Media> mediaItems = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public List<Album> getAlbums(){
        return albums;
    }
    public void setAlbum(List<Album> albums){
        this.albums = albums;
    }
}
