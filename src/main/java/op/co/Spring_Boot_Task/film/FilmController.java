package op.co.Spring_Boot_Task.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import op.co.Spring_Boot_Task.actor.ActorController;
import op.co.Spring_Boot_Task.actor.ActorFilm;
import op.co.Spring_Boot_Task.actor.ActorFilmDTO;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping("api/films")
public class FilmController {

    private final FilmService filmService;
    private ActorController actorService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms(@RequestParam(required = false) Integer start,
                                                   @RequestParam(required = false) Integer limit,
                                                   @RequestParam(required = false) List<String> retrievedFields,
                                                   @RequestParam(required = false) List<String> excludedFields) {
        List<Film> films = filmService.getAllFilms(start, limit, retrievedFields, excludedFields);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }
    
    @GetMapping(path = "{filmId}")
public Film getFilmById(@PathVariable UUID filmId) {
    return filmService.getFilmById(filmId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found"));
}

@PutMapping(path = "{filmId}")
public void updateFilm(@PathVariable UUID filmId, @RequestBody @Valid Film film) {
    filmService.updateFilm(filmId, film);
}

@DeleteMapping(path = "{filmId}")
public void deleteFilm(@PathVariable UUID filmId) {
    filmService.deleteFilm(filmId);
}

@GetMapping(path = "{filmId}/actors")
public ResponseEntity<List<ActorFilmDTO>> getAllActorsForFilm(@PathVariable UUID filmId) {
    List<ActorFilmDTO> actorFilmDTOs = filmService.getAllActorsForFilm(filmId);
    return new ResponseEntity<>(actorFilmDTOs, HttpStatus.OK);
}
@GetMapping("/actorfilms")
public List<ActorFilm> getAllActorFilms() {
    return filmService.getAllActorFilms();
}
@GetMapping(path = "/actors/{actorId}/films")
public ResponseEntity<List<ActorFilmDTO>> getAllFilmsForActor(@PathVariable UUID actorId) {
    List<ActorFilmDTO> actorFilmDTOs = filmService.getAllFilmsForActor(actorId);
    return new ResponseEntity<>(actorFilmDTOs, HttpStatus.OK);
}
    @PostMapping
    public void addNewFilm(@RequestBody @Valid Film film) {
        filmService.addNewFilm(film);
    }
    @PostMapping(path = "/actorfilms")
public ResponseEntity<String> addActorToFilm(@RequestBody @Valid ActorFilm actorFilm) {
    try {
        filmService.addActorToFilm(actorFilm);
        return new ResponseEntity<>("Actor added to film successfully", HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

@DeleteMapping(path = "/actors/{actorId}/films/{filmId}")
public ResponseEntity<Void> deleteActorFromFilm(@PathVariable UUID actorId, @PathVariable UUID filmId) {
    try {
        filmService.deleteActorFromFilm(actorId, filmId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (ResponseStatusException e) {
        return new ResponseEntity<>(e.getStatus());
    }
}


@PutMapping(path = "/actors/{actorId}/films/{filmId}")
public ResponseEntity<ActorFilmDTO> updateActorFilm(@PathVariable UUID actorId, @PathVariable UUID filmId, @RequestBody @Valid ActorFilm updatedActorFilm) {
    try {
        ActorFilmDTO actorFilmDTO = filmService.updateActorFilm(actorId, filmId, updatedActorFilm);
        return new ResponseEntity<>(actorFilmDTO, HttpStatus.OK);
    } catch (ResponseStatusException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}





}