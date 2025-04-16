package ru.itgirl.library_project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllAuthors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(view().name("authorsTable"));
    }

    @Test
    void getAuthorById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Александр"))
                .andExpect(jsonPath("$.surname").value("Пушкин"));
    }

    @Test
    void getAuthorByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/name").param("name", "Александр")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Александр"))
                .andExpect(jsonPath("$.surname").value("Пушкин"));
    }

    @Test
    void getAuthorByNameSql() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/name/sql").param("name", "Александр")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Александр"));
    }

    @Test
    void getAuthorByNameSpec() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/name/spec")
                        .param("name", "Александр").param("surname", "Пушкин")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(view().name("authors/name/spec"))
                .andExpect(model().attributeExists("authorDtoList"))
                .andExpect(model().attribute("authorDtoList", hasItem(
                        allOf(
                                hasProperty("name", is("Александр")),
                                hasProperty("surname", is("Пушкин"))
                        )
                )));
    }
}