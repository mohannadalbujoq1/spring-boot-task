
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import op.co.Spring_Boot_Task.EmailValidator;
import op.co.Spring_Boot_Task.actor.Actor;
import op.co.Spring_Boot_Task.actor.ActorRepository;
import op.co.Spring_Boot_Task.actor.ActorService;
import op.co.Spring_Boot_Task.exception.ApiRequestException;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private EmailValidator emailValidator;

    @InjectMocks
    private ActorService actorService;

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor();
        actor.setActorId(UUID.randomUUID());
        actor.setFirstName("John");
        actor.setLastName("Doe");
        actor.setEmail("john.doe@example.com");
    }

    @Test
    void addNewActor_ValidEmail_Success() {
        when(emailValidator.test(actor.getEmail())).thenReturn(true);
        when(actorRepository.isEmailTaken(actor.getEmail())).thenReturn(false);

        actorService.addNewActor(actor);

        verify(actorRepository).save(actor);
    }

    @Test
    void addNewActor_InvalidEmail_ThrowsException() {
        when(emailValidator.test(actor.getEmail())).thenReturn(false);

        assertThrows(ApiRequestException.class, () -> actorService.addNewActor(actor));
    }

    @Test
    void addNewActor_EmailTaken_ThrowsException() {
        when(emailValidator.test(actor.getEmail())).thenReturn(true);
        when(actorRepository.isEmailTaken(actor.getEmail())).thenReturn(true);

        assertThrows(ApiRequestException.class, () -> actorService.addNewActor(actor));
    }

    @Test
    void updateActor_ExistingActor_Success() {
        UUID actorId = actor.getActorId();
        when(actorRepository.existsById(actorId)).thenReturn(true);

        actorService.updateActor(actorId, actor);

        verify(actorRepository).save(actor);
    }

    @Test
    void updateActor_NonExistingActor_ThrowsException() {
        UUID actorId = actor.getActorId();
        when(actorRepository.existsById(actorId)).thenReturn(false);

        assertThrows(ApiRequestException.class, () -> actorService.updateActor(actorId, actor));
    }


}