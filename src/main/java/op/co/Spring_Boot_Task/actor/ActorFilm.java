package op.co.Spring_Boot_Task.actor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import op.co.Spring_Boot_Task.actor.ActorFilmId;

@Entity
@Table(name = "actor_film")
@IdClass(ActorFilmId.class) 
public class ActorFilm {
    @Id
    @Column(name = "actor_id")
    private UUID actorId;
    
    @Id
    @Column(name = "film_id")
    private UUID filmId;

    public ActorFilm() {
    }

    public ActorFilm(UUID actorId, UUID filmId) {
        this.actorId = actorId;
        this.filmId = filmId;
    }

    public UUID getActorId() {
        return actorId;
    }

    public void setActorId(UUID actorId) {
        this.actorId = actorId;
    }

    public UUID getFilmId() {
        return filmId;
    }

    public void setFilmId(UUID filmId) {
        this.filmId = filmId;
    }
}