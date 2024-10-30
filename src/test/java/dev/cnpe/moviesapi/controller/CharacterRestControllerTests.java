package dev.cnpe.moviesapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cnpe.moviesapi.model.dto.CharacterCreationRequest;
import dev.cnpe.moviesapi.model.dto.CharacterUpdateRequest;
import dev.cnpe.moviesapi.model.service.CharacterService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CharacterRestControllerTests {

    public static final String BASE_URL = "/api/v1/characters";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterService characterService;

    //Needed for serializing/deserializing LocalDate, Instant, etc.
//    private final static ObjectMapper jackson = new ObjectMapper().registerModule(new JavaTimeModule());
    @Autowired
    private ObjectMapper mapper;

    @Nested
    @WithMockUser
    class WhenAuthenticated {

        @Test
        void searchShouldReturnAllCharactersWhenNoParams() throws Exception {
            mockMvc.perform(get(BASE_URL))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.content.length()").value(5));
        }

        @Test
        void searchShouldReturnFilteredByName() throws Exception {
            mockMvc.perform(get(BASE_URL)
                           .param("name", "test"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.content.length()").value(3));
        }

        @Test
        void searchShouldReturnFilteredByMovieTitle() throws Exception {
            mockMvc.perform(get(BASE_URL)
                           .param("movieTitle", "Delta"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.content.length()").value(2))
                   .andExpect(jsonPath("$.content[0].name").value("Test Character A"))
                   .andExpect(jsonPath("$.content[1].name").value("Fake Character D"));
        }

        @Test
        void searchShouldReturnFilteredByAge() throws Exception {
            mockMvc.perform(get(BASE_URL)
                           .param("age", "20"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.content.length()").value(1))
                   .andExpect(jsonPath("$.content[0].name").value("Test Character A"));
        }

        @Test
        void shouldReturnCharacterWhenDataIsSaved() throws Exception {
            mockMvc.perform(get(BASE_URL + "/1"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.id").value(1))
                   .andExpect(jsonPath("$.name").value("Test Character A"))
                   .andExpect(jsonPath("$.image").value("char1.jpg"))
                   .andExpect(jsonPath("$.age").value(20))
                   .andExpect(jsonPath("$.weight").value(70.5))
                   .andExpect(jsonPath("$.story").value("Main protagonist for testing"))
                   .andExpect(jsonPath("$.movies.length()").value(5));
        }

        @Test
        @DirtiesContext
        void shouldCreateCharacter() throws Exception {

            CharacterCreationRequest creationRequest =
                    CharacterCreationRequest.builder()
                                            .image("image-test.jpg")
                                            .name("Test character")
                                            .age(666)
                                            .weight(66.6)
                                            .story("Test story")
                                            .build();


            String location = mockMvc.perform(post(BASE_URL)
                                             .with(csrf())
                                             .content(mapper.writeValueAsString(creationRequest))
                                             .contentType(MediaType.APPLICATION_JSON)
                                     )
                                     .andExpect(status().is2xxSuccessful())
                                     .andExpect(header().exists("Location"))
                                     .andReturn().getResponse().getHeader("Location");

            assertThat(location).isNotNull();

            mockMvc.perform(get(location))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.name").value("Test character"))
                   .andExpect(jsonPath("$.image").value("image-test.jpg"))
                   .andExpect(jsonPath("$.age").value(666))
                   .andExpect(jsonPath("$.weight").value(66.6))
                   .andExpect(jsonPath("$.story").value("Test story"))
                   .andExpect(jsonPath("$.movies.length()").value(0));
        }

        @Test
        @DirtiesContext
        void shouldDeleteCharacterIfPresent() throws Exception {

            mockMvc.perform(get(BASE_URL + "/1"))
                   .andExpect(status().isOk());

            mockMvc.perform(delete(BASE_URL + "/1"))
                   .andExpect(status().isNoContent());

            mockMvc.perform(get(BASE_URL + "/1"))
                   .andExpect(status().isNotFound());

        }

        @Test
        void shouldReturnBadRequestIsNoCharacter() throws Exception {
            mockMvc.perform(delete(BASE_URL + "/99112266"))
                   .andExpect(status().isNotFound());
        }

        @Test
        @DirtiesContext
        void shouldUpdateCharacter() throws Exception {
            CharacterUpdateRequest updateRequest = CharacterUpdateRequest.builder()
                                                                         .image("updated-image.png")
                                                                         .name("Updated Character Name")
                                                                         .age(100)
                                                                         .weight(100.0)
                                                                         .story("Updated story")
                                                                         .build();

            mockMvc.perform(put(BASE_URL + "/1")
                           .content(mapper.writeValueAsString(updateRequest))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andExpect(status().isNoContent());

            mockMvc.perform(get(BASE_URL + "/1"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.name").value("Updated Character Name"))
                   .andExpect(jsonPath("$.image").value("updated-image.png"))
                   .andExpect(jsonPath("$.age").value(100))
                   .andExpect(jsonPath("$.weight").value(100.0))
                   .andExpect(jsonPath("$.story").value("Updated story"));
        }

        @Test
        @DirtiesContext
        void shouldUpdateOnlyGivenFields() throws Exception {
            CharacterUpdateRequest updateRequest = CharacterUpdateRequest.builder()
                                                                         .name("Updated Character Name")
                                                                         .weight(100.0)
                                                                         .build();

            mockMvc.perform(put(BASE_URL + "/1")
                           .with(csrf())
                           .content(mapper.writeValueAsString(updateRequest))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andExpect(status().isNoContent());

            mockMvc.perform(get(BASE_URL + "/1"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.name").value("Updated Character Name"))
                   .andExpect(jsonPath("$.image").value("char1.jpg"))
                   .andExpect(jsonPath("$.age").value(20))
                   .andExpect(jsonPath("$.weight").value(100.0))
                   .andExpect(jsonPath("$.story").value("Main protagonist for testing"));
        }
    }


}
