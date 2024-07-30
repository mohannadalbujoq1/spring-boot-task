
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import op.co.Spring_Boot_Task.film.Film;
import op.co.Spring_Boot_Task.film.FilmController;
import op.co.Spring_Boot_Task.film.FilmService;

@WebMvcTest(op.co.Spring_Boot_Task.film.FilmController.class)
@ContextConfiguration(classes = op.co.Spring_Boot_Task.SpringBootTaskApplication.class)

public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @Autowired
    private ObjectMapper objectMapper;

    private Film testFilm;

    @BeforeEach
    void setUp() {
        testFilm = new Film();
        testFilm.setFilmId(UUID.randomUUID());
        testFilm.setTitle("Test Film");
        testFilm.setReleaseDate(LocalDate.of(2020, 1, 8));
        testFilm.setGenre("Drama");
        testFilm.setDuration(120);
        testFilm.setDirectorId(UUID.randomUUID());
    }

    @Test
    void getAllFilmsShouldReturnFilms() throws Exception {
        when(filmService.getAllFilms(null, null, null, null)).thenReturn(Collections.singletonList(testFilm));

        mockMvc.perform(get("/api/films")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(testFilm.getTitle()));
    }

    @Test
    void getFilmByIdShouldReturnFilm() throws Exception {
        when(filmService.getFilmById(testFilm.getFilmId())).thenReturn(java.util.Optional.of(testFilm));

        mockMvc.perform(get("/api/films/{filmId}", testFilm.getFilmId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testFilm.getTitle()));
    }

    @Test
    void updateFilmShouldUpdateFilm() throws Exception {
        mockMvc.perform(put("/api/films/{filmId}", testFilm.getFilmId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().isOk());

        verify(filmService, times(1)).updateFilm(eq(testFilm.getFilmId()), any(Film.class));
    }

    @Test
    void deleteFilmShouldRemoveFilm() throws Exception {
        mockMvc.perform(delete("/api/films/{filmId}", testFilm.getFilmId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(filmService, times(1)).deleteFilm(testFilm.getFilmId());
    }
}