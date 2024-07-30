import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.UUID;
import javax.transaction.Transactional;

import op.co.Spring_Boot_Task.actor.Actor;
import op.co.Spring_Boot_Task.actor.Actor.Gender;
import op.co.Spring_Boot_Task.actor.ActorRepository;
import op.co.Spring_Boot_Task.film.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = op.co.Spring_Boot_Task.SpringBootTaskApplication.class)

public class ActorTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ActorRepository actorRepository; 

    private Actor actor;

    @BeforeEach
    public void setUp() {
        actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");
        actor.setEmail("john.doe@example.com");
        actor.setGender(Gender.MALE);
        Film film = new Film(); 
        film.setTitle("Test Film");
        film.setActors(Collections.singleton(actor));
        actor.setFilms(Collections.singleton(film));
    }

    @Test
    @Transactional
    public void testActorPersistence() {
        Actor persistedActor = entityManager.persistAndFlush(actor);

        Actor foundActor = actorRepository.findById(persistedActor.getActorId()).orElse(null);

        assertThat(foundActor).isNotNull();
        assertThat(foundActor.getFirstName()).isEqualTo(actor.getFirstName());
        assertThat(foundActor.getLastName()).isEqualTo(actor.getLastName());
        assertThat(foundActor.getEmail()).isEqualTo(actor.getEmail());
        assertThat(foundActor.getGender()).isEqualTo(actor.getGender());
    }
}