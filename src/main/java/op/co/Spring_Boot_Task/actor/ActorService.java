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

    private final ActorRepository actorRepository;
    private final EmailValidator emailValidator;

    @Autowired
    public ActorService(ActorRepository actorRepository, EmailValidator emailValidator) {
        this.actorRepository = actorRepository;
        this.emailValidator = emailValidator;
    }

    public List<Actor> getAllActors() {
        return actorRepository.selectAllActors();
    }

    public Optional<Actor> getActorById(UUID actorId) {
        return actorRepository.findById(actorId);
    }

    public void addNewActor(Actor actor) {
        if (!emailValidator.test(actor.getEmail())) {
            throw new ApiRequestException(actor.getEmail() + " is not valid");
        }

        if (actorRepository.isEmailTaken(actor.getEmail())) {
            throw new ApiRequestException(actor.getEmail() + " is taken");
        }

        actorRepository.save(actor);
    }

    public void addActorToFilm(UUID filmId, UUID actorId) {
        actorRepository.addActorToFilm(filmId, actorId);
    }

    public void updateActor(UUID actorId, Actor actor) {
        if (!actorRepository.existsById(actorId)) {
            throw new ApiRequestException("Actor with ID " + actorId + " does not exist");
        }
        actor.setActorId(actorId);
        actorRepository.save(actor);
    }

    public void deleteActor(UUID actorId) {
        actorRepository.deleteActorById(actorId);
    }

    public int deleteActorFromFilm(UUID filmId, UUID actorId) {
        return actorRepository.deleteActorFromFilm(filmId, actorId);
    }

    public List<ActorFilmDTO> selectAllActorFilms(UUID actorId) {
        return actorRepository.selectAllActorFilms(actorId);
    }
}