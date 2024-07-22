package op.co.Spring_Boot_Task.actor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ActorDataAccessServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplateMock;

    @InjectMocks
    private ActorDataAccessService actorDataAccessService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void selectAllActors_ReturnsAllActors() {
        List<Actor> expectedActors = new ArrayList<>();
        expectedActors.add(new Actor(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", Actor.Gender.MALE));
        expectedActors.add(new Actor(UUID.randomUUID(), "Jane", "Doe", "jane.doe@example.com", Actor.Gender.FEMALE));

        when(jdbcTemplateMock.query(anyString(), any(RowMapper.class))).thenReturn(expectedActors);

        List<Actor> actualActors = actorDataAccessService.selectAllActors();

        assertNotNull(actualActors);
        assertEquals(expectedActors.size(), actualActors.size());
        assertEquals(expectedActors.get(0).getFirstName(), actualActors.get(0).getFirstName());
        assertEquals(expectedActors.get(1).getFirstName(), actualActors.get(1).getFirstName());
    }

    @Test
    public void insertActor_InsertsActor() {
        UUID actorId = UUID.randomUUID();
        Actor actor = new Actor(actorId, "John", "Doe", "john.doe@example.com", Actor.Gender.MALE);

        when(jdbcTemplateMock.update(anyString(), any(Object[].class))).thenReturn(1);

        int result = actorDataAccessService.insertActor(actorId, actor);

        assertEquals(1, result);
        verify(jdbcTemplateMock, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void deleteActor_DeletesActor() {
        UUID actorId = UUID.randomUUID();

        when(jdbcTemplateMock.update(anyString(), any(Object[].class))).thenReturn(1);

        int result = actorDataAccessService.deleteActorById(actorId);

        assertEquals(1, result);
        verify(jdbcTemplateMock, times(1)).update(anyString(), any(Object[].class));
    }
}