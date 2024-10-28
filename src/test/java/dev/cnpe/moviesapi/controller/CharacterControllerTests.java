package dev.cnpe.moviesapi.controller;

import dev.cnpe.moviesapi.model.service.CharacterService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CharacterControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterService characterService;

    @Nested
    @WithMockUser
    class WhenAuthenticated {

        @Test
        void searchShouldReturnAllCharactersWhenNoParams() throws Exception {
            mockMvc.perform(get("/api/v1/characters"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.length()").value(5));
        }

        @Test
        void searchShouldReturnFilteredByName() throws Exception {
            mockMvc.perform(get("/api/v1/characters?name=test"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.length()").value(3));
        }

        @Test
        void searchShouldReturnFilteredByMovieTitle() throws Exception {
            mockMvc.perform(get("/api/v1/characters?movieTitle=Delta"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.length()").value(2))
                   .andExpect(jsonPath("$[0].name").value("Test Character A"))
                   .andExpect(jsonPath("$[1].name").value("Fake Character D"));
        }

        @Test
        void searchShouldReturnFilteredByAge() throws Exception {
            mockMvc.perform(get("/api/v1/characters?age=20"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.length()").value(1))
                   .andExpect(jsonPath("$[0].name").value("Test Character A"));
        }

        @Test
        void shouldReturnCharacterWhenDataIsSaved() throws Exception {
            mockMvc.perform(get("/api/v1/characters/1"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.id").value(1))
                   .andExpect(jsonPath("$.name").value("Test Character A"))
                   .andExpect(jsonPath("$.image").value("char1.jpg"))
                   .andExpect(jsonPath("$.age").value(20))
                   .andExpect(jsonPath("$.weight").value(70.5))
                   .andExpect(jsonPath("$.story").value("Main protagonist for testing"))
                   .andExpect(jsonPath("$.movies.length()").value(5));
        }
    }


}
