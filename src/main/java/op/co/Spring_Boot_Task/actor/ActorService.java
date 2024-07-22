package op.co.Spring_Boot_Task.actor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import op.co.Spring_Boot_Task.EmailValidator;
import op.co.Spring_Boot_Task.exception.ApiRequestException;

@Service
public class ActorService {

    private final ActorDataAccessService actorDataAccessService;
    private final EmailValidator emailValidator;

    @Autowired
    public ActorService(ActorDataAccessService actorDataAccessService, EmailValidator emailValidator) {
        this.actorDataAccessService = actorDataAccessService;
        this.emailValidator = emailValidator;
    }

    public List<Actor> getAllActors() {
        return actorDataAccessService.selectAllActors();
    }

    public Optional<Actor> getActorById(UUID actorId) {
        return actorDataAccessService.selectActorById(actorId);
    }

    public void addNewActor(Actor actor) {
        addNewActor(null, actor);
    }


public void addNewActor(UUID actorId, Actor actor) {git add .
    UUID newActorId = Optional.ofNullable(actorId).orElse(UUID.randomUUID());

    if (!emailValidator.test(actor.getEmail())) {
        throw new ApiRequestException(actor.getEmail() + " is not valid");
    }

    if (actorDataAccessService.isEmailTaken(actor.getEmail())) {
        throw new ApiRequestException(actor.getEmail() + " is taken");
    }

    actorDataAccessService.insertActor(newActorId, actor);
}
public void addActorToFilm(UUID filmId, UUID actorId) {
    actorDataAccessService.addActorToFilm(filmId, actorId);
}

    public void updateActor(UUID actorId, Actor actor) {
        actorDataAccessService.updateActorById(actorId, actor);
    }

    public void deleteActor(UUID actorId) {
        actorDataAccessService.deleteActorById(actorId);
    }
public int deleteActorFromFilm(UUID filmId, UUID actorId) {
    return actorDataAccessService.deleteActorFromFilm(filmId, actorId);
}

public List<ActorFilm> selectAllActorFilms(UUID actorId) {
    return actorDataAccessService.selectAllActorFilms(actorId);
}
}