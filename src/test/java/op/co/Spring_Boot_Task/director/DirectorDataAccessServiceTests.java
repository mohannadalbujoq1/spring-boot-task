package op.co.Spring_Boot_Task.director;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DirectorDataAccessServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private DirectorDataAccessService directorDataAccessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insertDirector() {
        UUID directorId = UUID.randomUUID();
        Director director = new Director(directorId, "New", "Director", "new.director@example.com", LocalDate.now(), Director.Gender.MALE);
        when(jdbcTemplate.update(anyString(), any(UUID.class), anyString(), anyString(), anyString(), any(LocalDate.class), anyString())).thenReturn(1);
        assertEquals(1, directorDataAccessService.insertDirector(directorId, director));
    }

    @Test
    void selectAllDirectors() {
        Director director = new Director(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", LocalDate.of(1980, 1, 1), Director.Gender.MALE);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Arrays.asList(director));
        List<Director> directors = directorDataAccessService.selectAllDirectors();
        assertFalse(directors.isEmpty());
        assertEquals(director, directors.get(0));
    }

    @SuppressWarnings("deprecation")
    @Test
    void selectDirectorById() {
        UUID directorId = UUID.randomUUID();
        Director expectedDirector = new Director(directorId, "Jane", "Doe", "jane.doe@example.com", LocalDate.of(1985, 5, 15), Director.Gender.FEMALE);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(expectedDirector);
        Optional<Director> director = directorDataAccessService.selectDirectorById(directorId);
        assertTrue(director.isPresent());
        assertEquals(expectedDirector, director.get());
    }

    @Test
    void updateDirectorById() {
        UUID directorId = UUID.randomUUID();
        Director director = new Director(directorId, "Updated", "Director", "updated.director@example.com", LocalDate.now(), Director.Gender.FEMALE);
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString(), any(LocalDate.class), anyString(), any(UUID.class))).thenReturn(1);
        assertEquals(1, directorDataAccessService.updateDirectorById(directorId, director));
    }

    @Test
    void deleteDirectorById() {
        UUID directorId = UUID.randomUUID();
        when(jdbcTemplate.update(anyString(), any(UUID.class))).thenReturn(1);
        assertEquals(1, directorDataAccessService.deleteDirectorById(directorId));
    }
}