package op.co.Spring_Boot_Task.actor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ActorFilmId implements Serializable {

    private UUID actorId;
    private UUID filmId;

    public ActorFilmId() {
    }

    public ActorFilmId(UUID actorId, UUID filmId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActorFilmId)) return false;
        ActorFilmId that = (ActorFilmId) o;
        return Objects.equals(actorId, that.actorId) &&
                Objects.equals(filmId, that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, filmId);
    }
}
