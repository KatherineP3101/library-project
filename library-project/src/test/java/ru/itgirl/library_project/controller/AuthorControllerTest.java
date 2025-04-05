package ru.itgirl.library_project.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.model.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    void getAllAuthors() {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(view().name("authorsTable"));
    }

    @SneakyThrows
    @Test
    void getAuthorById() {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Жюль"))
                .andExpect(jsonPath("$.surname").value("Верн"));
    }

    @SneakyThrows
    @Test
    void getAuthorByName() {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/name").param("name", "Жюль")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Жюль"));
    }

    @SneakyThrows
    @Test
    void getAuthorByNameSql() {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/name/sql").param("name", "Жюль")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Жюль"));
    }

    @SneakyThrows
    @Test
    void getAuthorByNameSpec() {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/name/spec")
                        .param("name", "Жюль").param("surname", "Верн")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Жюль"))
                .andExpect(jsonPath("$[0].surname").value("Верн"));
    }
}