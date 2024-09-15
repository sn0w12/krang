CREATE TABLE media_artist (
    media_id BIGINT UNSIGNED,  
    artist_id BIGINT UNSIGNED,  
    PRIMARY KEY (media_id, artist_id),
    FOREIGN KEY (media_id) REFERENCES media(id),
    FOREIGN KEY (artist_id) REFERENCES artist(id)
);
