package op.co.Spring_Boot_Task.actor;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/actors")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping(path = "{actorId}")
    public Actor getActorById(@PathVariable("actorId") UUID actorId) {
        return actorService.getActorById(actorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found"));
    }
    
    @GetMapping(path = "films/{actorId}")
public List<ActorFilm> getAllFilmsForActor(@PathVariable("actorId") UUID actorId) {
    List<ActorFilm> films = actorService.selectAllActorFilms(actorId);
    if (films.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found or no films associated");
    }
    return films;
}

    @PostMapping
    public void addNewActor(@RequestBody @Valid Actor actor) {
        actorService.addNewActor(actor);
    }

    @PostMapping(path = "{filmId}/actors/{actorId}")
public void addActorToFilm(@PathVariable("filmId") UUID filmId, @PathVariable("actorId") UUID actorId) {
    actorService.addActorToFilm(filmId, actorId);
}

    @PutMapping(path = "{actorId}")
    public void updateActor(@PathVariable("actorId") UUID actorId, @RequestBody @Valid Actor actor) {
        actorService.updateActor(actorId, actor);
    }

    @DeleteMapping(path = "{actorId}")
    public void deleteActor(@PathVariable("actorId") UUID actorId) {
        actorService.deleteActor(actorId);
    }

@DeleteMapping(path = "{filmId}/actors/{actorId}")
public void deleteActorFromFilm(@PathVariable("filmId") UUID filmId, @PathVariable("actorId") UUID actorId) {
    int updateCount = actorService.deleteActorFromFilm(filmId, actorId);
    if (updateCount == 0) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor or Film not found");
    }
}



}