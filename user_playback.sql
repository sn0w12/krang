CREATE TABLE IF NOT EXISTS user_playbacks (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    media_id BIGINT UNSIGNED NOT NULL,
    playback_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    duration INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (media_id) REFERENCES media(id) -- assuming media table exists
);
