package op.co.Spring_Boot_Task.actor;

import java.util.UUID;

public class ActorFilm {

    private UUID actorId;
    private UUID filmId;
    private String firstName;
    private String lastName;
    private String title;


    
    public ActorFilm() {
    }

    public ActorFilm(UUID actorId, UUID filmId) {
        this.actorId = actorId;
        this.filmId = filmId;

    }
    public ActorFilm(UUID actorId, UUID filmId, String firstName, String lastName, String title) {
        this.actorId = actorId;
        this.filmId = filmId;

        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
    }

    public UUID getActorId() {
        return actorId;
    }


    public UUID getFilmId() {
        return filmId;
    }
 public String getFirstName() { 
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }    
    public String getTitle() {
        return title;
    }
}