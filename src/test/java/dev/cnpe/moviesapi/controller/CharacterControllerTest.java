package dev.cnpe.moviesapi.controller;

import dev.cnpe.moviesapi.model.dto.CharacterSearchCriteria;
import dev.cnpe.moviesapi.model.dto.CharacterSearchResult;
import dev.cnpe.moviesapi.model.service.CharacterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CharacterController.class)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterService characterService;

    @Test
    @WithMockUser
    void shouldReturnListOfCharacters() throws Exception {

        given(characterService.search(any(CharacterSearchCriteria.class)))
                .willReturn(List.of(
                                new CharacterSearchResult(1L, "Movie 1", "https://url.to/image-1"),
                                new CharacterSearchResult(2L, "Movie 2", "https://url.to/image-2"),
                                new CharacterSearchResult(3L, "Movie 3", "https://url.to/image-3")
                        )
                );

        mockMvc.perform(post("/api/v1/characters")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{}") //empty search criteria
               )
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()").value(3));
    }
}