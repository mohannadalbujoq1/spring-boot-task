
CREATE TABLE IF NOT EXISTS actor_film (
    actor_id UUID NOT NULL,
    film_id UUID NOT NULL,
    PRIMARY KEY (actor_id, film_id),
    FOREIGN KEY (actor_id) REFERENCES actor(actor_id) ON DELETE CASCADE,
    FOREIGN KEY (film_id) REFERENCES film(film_id) ON DELETE CASCADE
);

CREATE INDEX idx_director_id ON film(director_id);
CREATE INDEX idx_actor_id ON actor_film(actor_id);