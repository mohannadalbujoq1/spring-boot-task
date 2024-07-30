import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.UUID;
import javax.transaction.Transactional;

import op.co.Spring_Boot_Task.director.Director;
import op.co.Spring_Boot_Task.director.DirectorRepository;
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
public class DirectorTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DirectorRepository directorRepository;

    private Director director;

    @BeforeEach
    public void setUp() {
        director = new Director();
        director.setDirectorId(UUID.randomUUID()); // Assign UUID
        director.setFirstName("Jane");
        director.setLastName("Smith");
        director.setEmail("jane.smith@example.com");
        director.setDob(LocalDate.of(1975, 5, 15));
        director.setGender(Director.Gender.FEMALE);
    }

    @Test
    @Transactional
    public void testDirectorPersistence() {
        Director persistedDirector = entityManager.persistAndFlush(director);

        Director foundDirector = directorRepository.findById(persistedDirector.getDirectorId()).orElse(null);

        assertThat(foundDirector).isNotNull();
        assertThat(foundDirector.getFirstName()).isEqualTo(director.getFirstName());
        assertThat(foundDirector.getLastName()).isEqualTo(director.getLastName());
        assertThat(foundDirector.getEmail()).isEqualTo(director.getEmail());
        assertThat(foundDirector.getDob()).isEqualTo(director.getDob());
        assertThat(foundDirector.getGender()).isEqualTo(director.getGender());
    }
}