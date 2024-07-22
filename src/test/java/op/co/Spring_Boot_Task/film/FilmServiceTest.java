package op.co.Spring_Boot_Task.film;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import op.co.Spring_Boot_Task.actor.ActorFilm;
import static org.junit.jupiter.api.Assertions.*;

public class FilmServiceTest {

    @Mock
    private FilmDataAccessService filmDataAccessService;

    @InjectMocks
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addNewFilmWithIdTest();
    }

    @Test
    void addNewFilmWithIdTest() {
        UUID filmId = UUID.randomUUID();
        Film film = new Film(filmId, "Test Name", LocalDate.now(), "Test Genre", 120, UUID.randomUUID());
        filmService.addNewFilm(filmId, film);

        verify(filmDataAccessService).insertFilm(eq(filmId), any(Film.class));
    }

    @Test
    void addNewFilmWithoutIdTest() {
        Film film = new Film(null, "Test Name", LocalDate.now(), "Test Genre", 120, UUID.randomUUID());
        filmService.addNewFilm(film);

        verify(filmDataAccessService).insertFilm(any(UUID.class), eq(film));
    }

    @Test
    void getAllFilmsTest() {
        List<Film> expectedFilms = Arrays.asList(new Film(UUID.randomUUID(), "Test Name", LocalDate.now(), "Test Genre", 120, UUID.randomUUID()));
        when(filmDataAccessService.selectAllFilms()).thenReturn(expectedFilms);

        List<Film> actualFilms = filmService.getAllFilms();
        assertEquals(expectedFilms, actualFilms);
    }

    @Test
    void getFilmByIdTest() {
        UUID filmId = UUID.randomUUID();
        Optional<Film> expectedFilm = Optional.of(new Film(filmId, "Test Name", LocalDate.now(), "Test Genre", 120, filmId));
        when(filmDataAccessService.selectFilmById(filmId)).thenReturn(expectedFilm);

        Optional<Film> actualFilm = filmService.getFilmById(filmId);
        assertEquals(expectedFilm, actualFilm);
    }

    @Test
    void updateFilmTest() {
        UUID filmId = UUID.randomUUID();
Film film = new Film(filmId, "Test Name", LocalDate.now(), "Test Genre", 120, UUID.randomUUID());
        filmService.updateFilm(filmId, film);

        verify(filmDataAccessService).updateFilmById(filmId, film);
    }

    @Test
    void deleteFilmTest() {
        UUID filmId = UUID.randomUUID();
        filmService.deleteFilm(filmId);

        verify(filmDataAccessService).deleteFilmById(filmId);
    }


    @Test
    void getAllActorsForFilmTest() {
        UUID actorId = UUID.randomUUID();
        UUID filmId = UUID.randomUUID();
    
        ActorFilm actorFilm = new ActorFilm(actorId, filmId);

    }

}