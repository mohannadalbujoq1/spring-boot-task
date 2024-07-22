package op.co.Spring_Boot_Task.director;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

public class DirectorServiceTest {

    @Mock
    private DirectorDataAccessService directorDataAccessService;

    private DirectorService directorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        directorService = new DirectorService(directorDataAccessService, null);
    }

    @Test
    void addNewDirector() {
        UUID directorId = UUID.randomUUID();
        Director director = new Director(directorId, "John", "Doe", "john.doe@example.com", LocalDate.of(1980, 1, 1), Director.Gender.MALE);

        when(directorDataAccessService.insertDirector(any(UUID.class), eq(director))).thenReturn(1);

        int result = directorService.addNewDirector(director);

        assertEquals(1, result);
        verify(directorDataAccessService, times(1)).insertDirector(any(UUID.class), eq(director));
    }

    @Test
    void deleteDirector() {
        UUID directorId = UUID.fromString("d3b07384-d113-4ec8-9e2f-42287f745f00");
        when(directorDataAccessService.deleteDirectorById(directorId)).thenReturn(1);

        int result = directorService.deleteDirector(directorId);

        assertEquals(1, result);
        verify(directorDataAccessService, times(1)).deleteDirectorById(directorId);
    }

    @Test
    void updateDirectorTest() {
        UUID directorId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        Director director = new Director(directorId, "Jane", "Doe", "jane.doe@example.com", LocalDate.of(1985, 5, 15), Director.Gender.FEMALE);

        when(directorDataAccessService.updateDirectorById(directorId, director)).thenReturn(1);

        int result = directorService.updateDirector(directorId, director);

        assertEquals(1, result);
        verify(directorDataAccessService, times(1)).updateDirectorById(directorId, director);
    }
}