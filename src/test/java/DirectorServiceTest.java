
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import op.co.Spring_Boot_Task.director.Director;
import op.co.Spring_Boot_Task.director.DirectorRepository;
import op.co.Spring_Boot_Task.director.DirectorService;
import op.co.Spring_Boot_Task.film.FilmRepository;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private DirectorService directorService;

    private Director director;

    @BeforeEach
    void setUp() {
        director = new Director();
        director.setDirectorId(UUID.randomUUID());
        director.setFirstName("Jane");
        director.setLastName("Doe");
        director.setEmail("jane.doe@example.com");
        director.setDob(LocalDate.of(1980, 1, 1));
        director.setGender(Director.Gender.MALE);
    }

    @Test
    void addNewDirector_Success() {
        when(directorRepository.save(any(Director.class))).thenReturn(director);

        Director savedDirector = directorService.addNewDirector(director);

        assertNotNull(savedDirector);
        verify(directorRepository).save(director);
    }

    @Test
    void updateDirector_ExistingDirector_Success() {
        UUID directorId = director.getDirectorId();
        when(directorRepository.findById(directorId)).thenReturn(Optional.of(director));

        Director updatedDirector = new Director();
        updatedDirector.setFirstName("Updated Name");
        directorService.updateDirector(directorId, updatedDirector);

        verify(directorRepository).save(director);
    }

    @Test
    void updateDirector_NonExistingDirector_AddsNew() {
        UUID directorId = UUID.randomUUID();
        when(directorRepository.findById(directorId)).thenReturn(Optional.empty());

        Director newDirector = new Director();
        directorService.updateDirector(directorId, newDirector);

        verify(directorRepository).save(newDirector);
    }

    @Test
    void deleteDirector_Success() {
        UUID directorId = director.getDirectorId();

        directorService.deleteDirector(directorId);

        verify(directorRepository).deleteById(directorId);
    }

    @Test
    void getAllDirectors_Success() {
        directorService.getAllDirectors();

        verify(directorRepository).findAll();
    }

    @Test
    void getDirectorById_Success() {
        UUID directorId = director.getDirectorId();
        when(directorRepository.findById(directorId)).thenReturn(Optional.of(director));

        Optional<Director> foundDirector = directorService.getDirectorById(directorId);

        assertTrue(foundDirector.isPresent());
        assertEquals(directorId, foundDirector.get().getDirectorId());
    }
}