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

import op.co.Spring_Boot_Task.actor.ActorFilm;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping("api/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }
    @GetMapping(path = "{filmId}")
public Film getFilmById(@PathVariable("filmId") UUID filmId) {
    return filmService.getFilmById(filmId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found"));
}

@PutMapping(path = "{filmId}")
public void updateFilm(@PathVariable("filmId") UUID filmId, @RequestBody @Valid Film film) {
    filmService.updateFilm(filmId, film);
}

@DeleteMapping(path = "{filmId}")
public void deleteFilm(@PathVariable("filmId") UUID filmId) {
    filmService.deleteFilm(filmId);
}

    @GetMapping(path = "{filmId}/actors")
    public List<ActorFilm> getAllActorsForFilm(@PathVariable("filmId") UUID filmId) {
        return filmService.getAllActorsForFilm(filmId);
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
@GetMapping(path = "/director/{directorId}")
public List<Film> getAllFilmsForDirector(@PathVariable("directorId") UUID directorId, @RequestParam Integer start, @RequestParam Integer limit, @RequestParam List<String> retrievedFields, @RequestParam List<String> excludedFields) {
    return filmService.getAllFilmsForDirector(directorId, start, limit, retrievedFields, excludedFields);
}

@GetMapping(path = "/actor/{actorId}")
public List<Film> getAllFilmsForActor(@PathVariable("actorId") UUID actorId, @RequestParam Integer start, @RequestParam Integer limit, @RequestParam List<String> retrievedFields, @RequestParam List<String> excludedFields) {
    return filmService.getAllFilmsForActor(actorId, start, limit, retrievedFields, excludedFields);
}

}