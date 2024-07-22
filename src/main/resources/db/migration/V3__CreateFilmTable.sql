CREATE TABLE IF NOT EXISTS film (
    film_id UUID PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL UNIQUE,
    release_date DATE NOT NULL,
    genre VARCHAR(100) NOT NULL,
    duration INTEGER NOT NULL CHECK (duration > 0),
    director_id UUID NOT NULL,
    FOREIGN KEY (director_id) REFERENCES director(director_id) ON DELETE CASCADE
);