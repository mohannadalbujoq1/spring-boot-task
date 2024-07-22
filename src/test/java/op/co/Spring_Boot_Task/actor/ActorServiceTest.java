package op.co.Spring_Boot_Task.actor;

import op.co.Spring_Boot_Task.EmailValidator;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

public class ActorServiceTest {

    @Mock
    private ActorDataAccessService actorDataAccessService;

    private ActorService actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        EmailValidator emailValidator = new EmailValidator();
        actorService = new ActorService(actorDataAccessService, emailValidator);
    }

    @Test
    void addNewActor_AddsActorToDatabase() {
        UUID actorId = UUID.randomUUID();
        Actor actor = new Actor(actorId, "John", "Doe", "john.doe@example.com", Actor.Gender.MALE);
        when(actorDataAccessService.insertActor(any(UUID.class), any(Actor.class))).thenReturn(1);
        actorService.addNewActor(actor);
        verify(actorDataAccessService, times(1)).insertActor(any(UUID.class), any(Actor.class));
    }

    @Test
    void deleteActor_RemovesActorFromDatabase() {
        UUID actorId = UUID.randomUUID();
        when(actorDataAccessService.deleteActorById(actorId)).thenReturn(1);
        actorService.deleteActor(actorId);
        verify(actorDataAccessService, times(1)).deleteActorById(actorId);
    }
}