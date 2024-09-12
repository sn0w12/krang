package com.example.krang.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "album", schema = "KRANG")
public class Album {

    private String title;
    private int releaseYear;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
