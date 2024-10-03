-- Insert sample data for Artist (only if they don't already exist)
INSERT IGNORE INTO artist (id, name) VALUES
(1, 'The Beatles'),
(2, 'Led Zeppelin'),
(3, 'Pink Floyd');

-- Insert sample data for Album (only if they don't already exist)
INSERT IGNORE INTO album (id, title, release_year, artist_id) VALUES
(1, 'Abbey Road', 1969, 1),
(2, 'Led Zeppelin IV', 1971, 2),
(3, 'The Dark Side of the Moon', 1973, 3);

-- Insert sample data for Media (only if they don't already exist)
INSERT IGNORE INTO media (id, title, genre, media_type, artist_id, release_date, stream_url, album_id) VALUES
(1, 'Come Together', 'Rock', 'Audio', 1, '1969-09-26', 'https://example.com/come-together', 1),
(2, 'Stairway to Heaven', 'Rock', 'Audio', 2, '1971-11-08', 'https://example.com/stairway-to-heaven', 2),
(3, 'Money', 'Progressive Rock', 'Audio', 3, '1973-03-01', 'https://example.com/money', 3);

-- Insert sample data for User (only if they don't already exist)
INSERT IGNORE INTO users (id, username, email, password, created_at, updated_at) VALUES
(1, 'john_doe', 'john@example.com', 'hashed_password_1', '2023-01-01T12:00:00', '2023-01-01T12:00:00'),
(2, 'jane_doe', 'jane@example.com', 'hashed_password_2', '2023-02-15T12:00:00', '2023-02-15T12:00:00'),
(3, 'alice_smith', 'alice@example.com', 'hashed_password_3', '2023-03-10T12:00:00', '2023-03-10T12:00:00');
