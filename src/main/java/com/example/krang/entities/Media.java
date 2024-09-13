package com.example.krang.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "url", length = 100)
    private String url;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
