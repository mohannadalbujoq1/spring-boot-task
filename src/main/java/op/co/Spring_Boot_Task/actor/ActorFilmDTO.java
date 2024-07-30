package op.co.Spring_Boot_Task.actor;

import java.util.UUID;

public class ActorFilmDTO {
    private UUID actorId;
    private UUID filmId;
    private String firstName;
    private String lastName;
    private String title;


    public ActorFilmDTO() {
    }

    public ActorFilmDTO(UUID actorId, UUID filmId, String firstName, String lastName, String title) {
        this.actorId = actorId;
        this.filmId = filmId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}