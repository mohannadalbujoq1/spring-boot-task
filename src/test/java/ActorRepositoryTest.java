
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import op.co.Spring_Boot_Task.SpringBootTaskApplication;
import op.co.Spring_Boot_Task.actor.Actor;
import op.co.Spring_Boot_Task.actor.ActorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = SpringBootTaskApplication.class)

public class ActorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ActorRepository actorRepository;

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");
        actor.setEmail("john.doe@example.com");
        actor.setGender(Actor.Gender.MALE);
        entityManager.persistAndFlush(actor);
    }

    @Test
    void whenFindActorByEmail_thenReturnActor() {
        Optional<Actor> foundActor = actorRepository.findActorByEmail(actor.getEmail());
        assertThat(foundActor).isPresent();
        assertThat(foundActor.get().getEmail()).isEqualTo(actor.getEmail());
    }

    @Test
    void whenFindActorsByFirstName_thenReturnActors() {
        List<Actor> foundActors = actorRepository.findActorsByFirstName(actor.getFirstName());
        assertThat(foundActors).hasSize(1);
        assertThat(foundActors.get(0).getFirstName()).isEqualTo(actor.getFirstName());
    }



    @Test
    void whenSelectAllActors_thenReturnAllActors() {
        List<Actor> allActors = actorRepository.selectAllActors();
        assertThat(allActors).hasSize(1);
        assertThat(allActors.get(0).getEmail()).isEqualTo(actor.getEmail());
    }


    @Test
    void whenIsEmailTaken_thenReturnTrue() {
        boolean isTaken = actorRepository.isEmailTaken(actor.getEmail());
        assertThat(isTaken).isTrue();
    }

  }