CREATE TABLE genres
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    image VARCHAR(255) NOT NULL
);

CREATE TABLE movies
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR(200) NOT NULL,
    creation_date DATE         NOT NULL,
    rating        SMALLINT CHECK (rating >= 1 AND rating <= 5),
    image         VARCHAR(255) NOT NULL
);

CREATE TABLE characters
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(100)  NOT NULL,
    age    INTEGER       NOT NULL,
    weight DECIMAL(5, 2) NOT NULL,
    story  TEXT          NOT NULL,
    image  VARCHAR(255)  NOT NULL
);

CREATE TABLE movies_genres
(
    movie_id INTEGER REFERENCES movies (id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genres (id) ON DELETE CASCADE,
    PRIMARY KEY (movie_id, genre_id)
);

CREATE TABLE movies_characters
(
    movie_id     INTEGER REFERENCES movies (id) ON DELETE CASCADE,
    character_id INTEGER REFERENCES characters (id) ON DELETE CASCADE,
    PRIMARY KEY (movie_id, character_id)
);

CREATE INDEX idx_movies_title ON movies (title);
CREATE INDEX idx_characters_name ON characters (name);
CREATE INDEX idx_genres_name ON genres (name);