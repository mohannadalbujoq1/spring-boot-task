import op.co.Spring_Boot_Task.actor.ActorController;
import op.co.Spring_Boot_Task.actor.ActorService;
import op.co.Spring_Boot_Task.actor.Actor;

import op.co.Spring_Boot_Task.SpringBootTaskApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActorController.class)
@ContextConfiguration(classes = SpringBootTaskApplication.class)
public class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorService actorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", Actor.Gender.MALE);
    }

    @Test
    void deleteActorTest() throws Exception {
        mockMvc.perform(delete("/api/actors/{actorId}", actor.getActorId()))
                .andExpect(status().isOk());

        verify(actorService).deleteActor(actor.getActorId());
    }

    @Test
    void addActorToFilmTest() throws Exception {
        UUID filmId = UUID.randomUUID();
        mockMvc.perform(post("/api/actors/{filmId}/actors/{actorId}", filmId, actor.getActorId()))
                .andExpect(status().isOk());

        verify(actorService).addActorToFilm(filmId, actor.getActorId());
    }


}