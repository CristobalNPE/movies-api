package dev.cnpe.moviesapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void createCharacterShouldRedirectOnSuccess() throws Exception {


        mockMvc.perform(post("/characters")
                       .with(csrf())
                       .param("image", "image-test.jpg")
                       .param("name", "TEST-CHARACTER-0123")
                       .param("age", "60")
                       .param("weight", "66.6")
                       .param("story", "Test story with valid content."))
               .andExpect(status().isFound())
               .andExpect(redirectedUrlPattern("/characters/*"));

        mockMvc.perform(get("/characters"))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("TEST-CHARACTER-0123")));
    }

    @Test
    void shouldReturnToFormWithErrorsOnInvalidData() throws Exception {
        mockMvc.perform(post("/characters")
                       .with(csrf())
                       .param("name", "?"))
               .andExpect(status().isOk())
               .andExpect(model().attributeHasErrors("character"));
    }
}