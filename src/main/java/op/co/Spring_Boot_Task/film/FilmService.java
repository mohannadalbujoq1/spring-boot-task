package op.co.Spring_Boot_Task.film;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import op.co.Spring_Boot_Task.actor.ActorFilm;

@Service
public class FilmService {

    private final FilmDataAccessService filmDataAccessService;

    public FilmService(FilmDataAccessService filmDataAccessService) {
        this.filmDataAccessService = filmDataAccessService;
    }

    public List<Film> getAllFilms() {
        return filmDataAccessService.selectAllFilms();
    }

    void addNewFilm(Film film) {
        addNewFilm(null, film);
    }

    void addNewFilm(UUID filmId, Film film) {
        UUID newFilmId = Optional.ofNullable(filmId).orElse(UUID.randomUUID());
        filmDataAccessService.insertFilm(newFilmId, film);
    }

    public Optional<Film> getFilmById(UUID filmId) {
        return filmDataAccessService.selectFilmById(filmId);
    }
    
    public void updateFilm(UUID filmId, Film film) {
        filmDataAccessService.updateFilmById(filmId, film);
    }
    
    public void deleteFilm(UUID filmId) {
        filmDataAccessService.deleteFilmById(filmId);
    }

    public List<ActorFilm> getAllActorsForFilm(UUID filmId) {
        return filmDataAccessService.selectAllFilmActors(filmId);
    }

    public void addActorToFilm(@Valid ActorFilm actorFilm) {
        UUID filmId = UUID.fromString(actorFilm.getFilmId().toString());
        UUID actorId = UUID.fromString(actorFilm.getActorId().toString());
        filmDataAccessService.addActorToFilm(filmId, actorId);
    }
    public List<Film> getAllFilmsForDirector(UUID directorId, Integer start, Integer limit, List<String> retrievedFields, List<String> excludedFields) {
        return filmDataAccessService.selectAllFilmsForDirectorWithPagination(directorId, start, limit, retrievedFields, excludedFields);
    }
    
    public List<Film> getAllFilmsForActor(UUID actorId, Integer start, Integer limit, List<String> retrievedFields, List<String> excludedFields) {
        return filmDataAccessService.selectAllFilmsForActorWithPagination(actorId, start, limit, retrievedFields, excludedFields);
    }
}