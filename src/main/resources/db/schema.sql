DROP TABLE IF EXISTS movies_characters CASCADE;
DROP TABLE IF EXISTS movies_genres CASCADE;
DROP TABLE IF EXISTS characters CASCADE;
DROP TABLE IF EXISTS movies CASCADE;
DROP TABLE IF EXISTS genres CASCADE;

CREATE TABLE IF NOT EXISTS genres
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    image VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS movies
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR(200) NOT NULL,
    creation_date DATE         NOT NULL,
    rating        VARCHAR(20),
    image         VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS characters
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(100)  NOT NULL,
    age    INTEGER       NOT NULL,
    weight DECIMAL(5, 2) NOT NULL,
    story  TEXT          NOT NULL,
    image  VARCHAR(255)  NOT NULL
);

CREATE TABLE IF NOT EXISTS movies_genres
(
    movie_id INTEGER REFERENCES movies (id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genres (id) ON DELETE CASCADE,
    PRIMARY KEY (movie_id, genre_id)
);

CREATE TABLE IF NOT EXISTS movies_characters
(
    movie_id     INTEGER REFERENCES movies (id) ON DELETE CASCADE,
    character_id INTEGER REFERENCES characters (id) ON DELETE CASCADE,
    PRIMARY KEY (movie_id, character_id)
);

CREATE INDEX IF NOT EXISTS idx_movies_title ON movies (title);
CREATE INDEX IF NOT EXISTS idx_characters_name ON characters (name);
CREATE INDEX IF NOT EXISTS idx_genres_name ON genres (name);