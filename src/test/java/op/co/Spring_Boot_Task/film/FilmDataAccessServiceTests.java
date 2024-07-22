package op.co.Spring_Boot_Task.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import op.co.Spring_Boot_Task.actor.Actor;
import op.co.Spring_Boot_Task.actor.ActorFilm;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FilmDataAccessServiceTests {

    @Mock
    private FilmDataAccessService filmDataAccessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

@Test
void testInsertFilm() {
    UUID filmId = UUID.randomUUID();
    String title = "Film Title";
    LocalDate releaseDate = LocalDate.now();
    String genre = "Drama";
    Integer duration = 120;
    UUID directorId = UUID.randomUUID();

    Film film = new Film(filmId, title, releaseDate, genre, duration, directorId);

    when(filmDataAccessService.insertFilm(filmId, film)).thenReturn(1);

    int result = filmDataAccessService.insertFilm(filmId, film);

    assertEquals(1, result);
}

@Test
void testSelectAllFilmActors() {
    List<ActorFilm> expectedActors = List.of(
        new ActorFilm(UUID.randomUUID(), UUID.randomUUID())
    );

    when(filmDataAccessService.selectAllFilmActors(any(UUID.class))).thenReturn(expectedActors);

    List<ActorFilm> actualActors = filmDataAccessService.selectAllFilmActors(UUID.randomUUID());

    assertEquals(expectedActors.size(), actualActors.size());
}
}