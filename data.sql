-- Insert Genres
INSERT INTO genre (id, name) VALUES
                                 (1, 'Rock'),
                                 (2, 'Pop'),
                                 (3, 'Technology'),
                                 (4, 'Documentary');

-- Insert Artists
INSERT INTO artist (id, name) VALUES
                                  (1, 'Mark Knofler'),
                                  (2, 'B.B King'),
                                  (3, 'Andrew Tate'),
                                  (4, 'The Weekend'),
                                  (5, 'Adele'),
                                  (6, 'Cece Winans');

-- Insert Albums
INSERT INTO album (id, name, release_date)
VALUES
    (1, 'Sultans of Swing', '1970-09-13'),
    (2, 'The thrill is gone', '1980-03-30'),
    (3, 'The Modern Man', '2020-12-01'),
    (4, 'I feel it Coming', '2021-04-13');

-- Insert Media
INSERT INTO media (id, title, release_date, url, category, album_id)
VALUES
    (1, 'Sultans of Swing', '1970-09-13', 'https://spotify.com/music/sultans-of-swing ', 'MUSIC', 1),
    (2, 'The thrill is gone', '1980-03-30', 'https://spotify.com/music/thrill-is-gone', 'MUSIC', 2),
    (3, 'The Modern Man', '2020-12-01', 'https://spotify.com/podcasts/tech-talk-1', 'PODCAST', 3),
    (4, 'I feel it Coming', '2021-04-13', 'https://youtube.com/videos/i-feel-it-coming', 'VIDEO', 4);

-- Insert into media_genre link table
INSERT INTO media_genre (media_id, genre_id) VALUES
                                                 (1, 1), -- 'Sultans of Swing' is 'Rock'
                                                 (2, 1), -- 'The thrill is gone' is 'Rock'
                                                 (3, 3), -- 'The Modern Man' is 'Technology'
                                                 (4, 4); -- 'I feel it Coming' is 'Documentary'

-- Insert into media_artist link table
INSERT INTO media_artist (media_id, artist_id) VALUES
                                                   (1, 1), -- 'Sultans of Swing' by 'Mark Knofler'
                                                   (2, 2), -- 'The thrill is gone' by 'B.B King'
                                                   (3, 3), -- 'The Modern Man' by 'Andrew Tate'
                                                   (4, 4); -- 'I feel it Coming' by 'The Weekend'
