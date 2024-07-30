import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import op.co.Spring_Boot_Task.SpringBootTaskApplication;
import op.co.Spring_Boot_Task.director.Director;
import op.co.Spring_Boot_Task.director.DirectorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = SpringBootTaskApplication.class)
public class DirectorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DirectorRepository directorRepository;

    private Director director;

    @BeforeEach
    void setUp() {
        director = new Director();
        director.setDirectorId(UUID.randomUUID()); // Assign UUID before persisting
        director.setFirstName("Jane");
        director.setLastName("Doe");
        director.setEmail("jane.doe@example.com");
        director.setDob(LocalDate.of(1980, 1, 1));
        director.setGender(Director.Gender.FEMALE);
        entityManager.persistAndFlush(director);
    }

    @Test
    void whenFindDirectorsByFirstName_thenReturnDirectors() {
        List<Director> foundDirectors = directorRepository.findDirectorsByFirstName(director.getFirstName());
        assertThat(foundDirectors).hasSize(1);
        assertThat(foundDirectors.get(0).getFirstName()).isEqualTo(director.getFirstName());
    }

    @Test
    void whenFindDirectorsByLastName_thenReturnDirectors() {
        List<Director> foundDirectors = directorRepository.findDirectorsByLastName(director.getLastName());
        assertThat(foundDirectors).hasSize(1);
        assertThat(foundDirectors.get(0).getLastName()).isEqualTo(director.getLastName());
    }

    @Test
    void whenFindDirectorByEmail_thenReturnDirector() {
        Optional<Director> foundDirector = directorRepository.findDirectorByEmail(director.getEmail());
        assertThat(foundDirector).isPresent();
        assertThat(foundDirector.get().getEmail()).isEqualTo(director.getEmail());
    }

    @Test
    void whenFindDirectorsByGender_thenReturnDirectors() {
        List<Director> foundDirectors = directorRepository.findDirectorsByGender(director.getGender());
        assertThat(foundDirectors).hasSize(1);
        assertThat(foundDirectors.get(0).getGender()).isEqualTo(director.getGender());
    }

}