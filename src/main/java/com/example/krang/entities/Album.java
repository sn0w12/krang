package com.example.krang.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "album", schema = "KRANG")
public class Album {
    private String title;
    private int releaseYear;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @OrderColumn(name = "media_order")
    private List<Media> mediaList;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Column(unique = true, nullable = false, length = 50)
    public String getTitle() {
        return title;
    }
    @Column(unique = true, nullable = false, length = 50)
    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public List<Media> getMediaList(){
        return mediaList;
    }
    public void setMediaList(List<Media>mediaList){
        this.mediaList = mediaList;
    }
    public void  setArtist(Artist artist){
        this.artist = artist;
    }
}
