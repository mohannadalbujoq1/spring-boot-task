
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import op.co.Spring_Boot_Task.film.Film;
import op.co.Spring_Boot_Task.film.FilmRepository;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = op.co.Spring_Boot_Task.SpringBootTaskApplication.class)

public class FilmRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FilmRepository filmRepository;

    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setTitle("Test Film");
        film.setReleaseDate(LocalDate.of(2020, 1, 1));
        film.setGenre("Drama");
        film.setDuration(120);
        film.setDirectorId(UUID.randomUUID());
        entityManager.persistAndFlush(film);
    }

    @Test
    void whenFindFilmByTitle_thenReturnFilm() {
        Optional<Film> foundFilm = filmRepository.findFilmByTitle(film.getTitle());
        assertThat(foundFilm).isPresent();
        assertThat(foundFilm.get().getTitle()).isEqualTo(film.getTitle());
    }

    @Test
    void whenFindFilmsByGenre_thenReturnFilms() {
        List<Film> foundFilms = filmRepository.findFilmsByGenre(film.getGenre());
        assertThat(foundFilms).hasSize(1);
        assertThat(foundFilms.get(0).getGenre()).isEqualTo(film.getGenre());
    }


    @Test
    void whenSelectAllFilms_thenReturnAllFilms() {
        List<Film> allFilms = filmRepository.selectAllFilms();
        assertThat(allFilms).hasSize(1);
        assertThat(allFilms.get(0).getTitle()).isEqualTo(film.getTitle());
    }

    @Test
    void whenFindFilmsByDirectorId_thenReturnFilms() {
        java.util.Collection<Film> foundFilms = filmRepository.findFilmsByDirectorId(film.getDirectorId());
        assertThat(foundFilms).hasSize(1);
        assertThat(foundFilms.iterator().next().getDirectorId()).isEqualTo(film.getDirectorId());
    }

    @Test
    void whenIsDirectorIdTaken_thenReturnTrue() {
        boolean isTaken = filmRepository.isDirectorIdTaken(film.getDirectorId());
        assertThat(isTaken).isTrue();
    }


}