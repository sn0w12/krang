CREATE TABLE IF NOT EXISTS media_genre (
    media_id BIGINT UNSIGNED NOT NULL,
    genre_id BIGINT UNSIGNED NOT NULL,  -- Ändra till BIGINT UNSIGNED för att matcha genre.id
    PRIMARY KEY (media_id, genre_id),
    FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);
