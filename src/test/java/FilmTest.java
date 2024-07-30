import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;
import javax.transaction.Transactional;

import op.co.Spring_Boot_Task.actor.Actor;
import op.co.Spring_Boot_Task.film.Film;
import op.co.Spring_Boot_Task.film.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = op.co.Spring_Boot_Task.SpringBootTaskApplication.class)
public class FilmTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FilmRepository filmRepository;

    private Film film;
    private Actor actor;

    @BeforeEach
    public void setUp() {
        film = new Film();
        film.setTitle("Test Film");
        film.setReleaseDate(LocalDate.of(2020, 1, 8));
        film.setGenre("Drama");
        film.setDuration(120);
        film.setDirectorId(UUID.randomUUID());

        actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");
        actor.setEmail("john.doe@example.com");
        actor.setGender(Actor.Gender.MALE);
    }

    @Test
    @Transactional
    public void testFilmPersistence() {
        // Save the actor first
        Actor persistedActor = entityManager.persistAndFlush(actor);
        film.setActors(Collections.singleton(persistedActor));

        Film persistedFilm = entityManager.persistAndFlush(film);

        Film foundFilm = filmRepository.findById(persistedFilm.getFilmId()).orElse(null);
        assertThat(foundFilm).isNotNull();
        assertThat(foundFilm.getTitle()).isEqualTo(film.getTitle());
        assertThat(foundFilm.getReleaseDate()).isEqualTo(film.getReleaseDate());
        assertThat(foundFilm.getGenre()).isEqualTo(film.getGenre());
        assertThat(foundFilm.getDuration()).isEqualTo(film.getDuration());
        assertThat(foundFilm.getDirectorId()).isEqualTo(film.getDirectorId());
    }
}