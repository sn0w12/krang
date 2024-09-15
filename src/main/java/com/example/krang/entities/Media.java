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

    @ElementCollection
    @CollectionTable(name = "media_genres", joinColumns = @JoinColumn(name = "media_id"))
    @Column(name = "genre")
    private List<String> genres;

    @Column(nullable = false)
    private String mediaType;

    // @ManyToMany
    // @JoinTable(name = "media_artists", joinColumns = @JoinColumn(name =
    // "media_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    // private List<Artist> artists;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date releaseDate;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Column
    private Integer trackOrder;

    @Column(nullable = false)
    private String streamingUrl;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    /*
     * public List<Artist> getArtists() {
     * return artists;
     * }
     *
     * public void setArtists(List<Artist> artists) {
     * this.artists = artists;
     * }
     */

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Integer getTrackOrder() {
        return trackOrder;
    }

    public void setTrackOrder(Integer trackOrder) {
        this.trackOrder = trackOrder;
    }

    public String getStreamingUrl() {
        return streamingUrl;
    }

    public void setStreamingUrl(String streamingUrl) {
        this.streamingUrl = streamingUrl;
    }
}