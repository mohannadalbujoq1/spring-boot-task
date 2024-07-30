import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import op.co.Spring_Boot_Task.actor.Actor;
import op.co.Spring_Boot_Task.actor.ActorFilm;
import op.co.Spring_Boot_Task.actor.ActorFilmDTO;
import op.co.Spring_Boot_Task.actor.ActorFilmId;
import op.co.Spring_Boot_Task.actor.ActorFilmRepository;
import op.co.Spring_Boot_Task.film.Film;
import op.co.Spring_Boot_Task.film.FilmRepository;
import op.co.Spring_Boot_Task.film.FilmService;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private ActorFilmRepository actorFilmRepository;

    @InjectMocks
    private FilmService filmService;

    private UUID actorId;
    private UUID filmId;
    private ActorFilm actorFilm;
    private Actor actor;
    private Film film;

    @BeforeEach
    void setUp() {
        actorId = UUID.randomUUID();
        filmId = UUID.randomUUID();
        actorFilm = new ActorFilm();
        actorFilm.setActorId(actorId);
        actorFilm.setFilmId(filmId);

        actor = new Actor();
        actor.setActorId(actorId);
        actor.setFirstName("John");
        actor.setLastName("Doe");

        film = new Film();
        film.setFilmId(filmId);
        film.setTitle("Test Film");
    }


    @Test
    void addActorToFilm_FilmOrActorNotExist_ThrowsException() {
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> filmService.addActorToFilm(actorFilm));
    }

    @Test
    void deleteActorFromFilm_ActorFilmExists_Success() {
        when(actorFilmRepository.findById(new ActorFilmId(actorId, filmId))).thenReturn(Optional.of(actorFilm));

        assertDoesNotThrow(() -> filmService.deleteActorFromFilm(actorId, filmId));
        verify(actorFilmRepository).delete(actorFilm);
    }

    @Test
    void deleteActorFromFilm_ActorFilmDoesNotExist_ThrowsException() {
        when(actorFilmRepository.findById(new ActorFilmId(actorId, filmId))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> filmService.deleteActorFromFilm(actorId, filmId));
    }

    @Test
    void updateActorFilm_ActorFilmDoesNotExist_ThrowsException() {
        when(actorFilmRepository.findById(new ActorFilmId(actorId, filmId))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> filmService.updateActorFilm(actorId, filmId, actorFilm));
    }

    @Test
    void getAllFilms_ReturnsFilteredFilms_Success() {
        List<Film> films = Arrays.asList(new Film(), new Film());
        when(filmRepository.findAll()).thenReturn(films);

        List<Film> result = filmService.getAllFilms(0, 2, Collections.emptyList(), Collections.emptyList());

        assertEquals(2, result.size());
        verify(filmRepository).findAll();
    }



    @Test
    void getFilmById_FilmExists_ReturnsFilm() {
        UUID filmId = UUID.randomUUID();
        Optional<Film> optionalFilm = Optional.of(new Film());
        when(filmRepository.findById(filmId)).thenReturn(optionalFilm);

        Optional<Film> result = filmService.getFilmById(filmId);

        assertTrue(result.isPresent());
        assertEquals(optionalFilm, result);
    }


    @Test
    void deleteFilm_DeletesFilm_Success() {
        UUID filmId = UUID.randomUUID();
        doNothing().when(filmRepository).deleteById(filmId);

        filmService.deleteFilm(filmId);

        verify(filmRepository).deleteById(filmId);
    }



    @Test
    void getAllActorFilms_ReturnsAllActorFilms_Success() {
        List<ActorFilm> actorFilms = Arrays.asList(new ActorFilm(), new ActorFilm());
        when(actorFilmRepository.findAll()).thenReturn(actorFilms);

        List<ActorFilm> result = filmService.getAllActorFilms();

        assertEquals(actorFilms.size(), result.size());
        verify(actorFilmRepository).findAll();
    }


}