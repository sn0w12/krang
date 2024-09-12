CREATE TABLE IF NOT EXISTS media_ratings (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    media_id BIGINT UNSIGNED NOT NULL,
    thumbs_up BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE KEY unique_user_media (user_id, media_id) -- Förhindra dubbla betyg på samma media
);
