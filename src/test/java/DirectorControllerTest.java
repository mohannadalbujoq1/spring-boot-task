
import com.fasterxml.jackson.databind.ObjectMapper;

import op.co.Spring_Boot_Task.director.Director;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(op.co.Spring_Boot_Task.director.DirectorController.class)
@ContextConfiguration(classes = op.co.Spring_Boot_Task.SpringBootTaskApplication.class)

public class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private op.co.Spring_Boot_Task.director.DirectorService directorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllDirectorsTest() throws Exception {
        Director director = new Director(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", LocalDate.of(1980, 1, 1), Director.Gender.MALE);
        Mockito.when(directorService.getAllDirectors()).thenReturn(Arrays.asList(director));

        mockMvc.perform(get("/api/director"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(director))));
    }

    @Test
    public void getDirectorByIdTest() throws Exception {
        UUID directorId = UUID.randomUUID();
        Director director = new Director(directorId, "John", "Doe", "john.doe@example.com", LocalDate.of(1980, 1, 1), Director.Gender.MALE);
        Mockito.when(directorService.getDirectorById(directorId)).thenReturn(java.util.Optional.of(director));

        mockMvc.perform(get("/api/director/{directorId}", directorId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(director)));
    }

    @Test
    public void addNewDirectorTest() throws Exception {
        Director director = new Director(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", LocalDate.of(1980, 1, 1), Director.Gender.MALE);

        mockMvc.perform(post("/api/director")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(director)))
                .andExpect(status().isOk());

        Mockito.verify(directorService).addNewDirector(Mockito.any(Director.class));
    }

    @Test
    public void updateDirectorTest() throws Exception {
        UUID directorId = UUID.randomUUID();
        Director director = new Director(directorId, "John", "Doe", "john.doe@example.com", LocalDate.of(1980, 1, 1), Director.Gender.MALE);

        mockMvc.perform(put("/api/director/{directorId}", directorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(director)))
                .andExpect(status().isOk());

        Mockito.verify(directorService).updateDirector(Mockito.eq(directorId), Mockito.any(Director.class));
    }

    @Test
    public void deleteDirectorTest() throws Exception {
        UUID directorId = UUID.randomUUID();

        mockMvc.perform(delete("/api/director/{directorId}", directorId))
                .andExpect(status().isOk());

        Mockito.verify(directorService).deleteDirector(directorId);
    }
}