package ru.itgirl.library_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.dto.UserDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.model.User;
import ru.itgirl.library_project.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private BookService bookService;

    @Test
    void createNewBook() throws Exception {
        List<AuthorDto> authors = new ArrayList<>();
        List<UserDto> users = new ArrayList<>();
        BookDto bookDto = new BookDto(1L, "Элантрис", "Роман", authors, users);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Элантрис"));
    }

    @Test
    void updateBook() throws Exception {
        List<AuthorDto> authors = new ArrayList<>();
        List<UserDto> users = new ArrayList<>();
        BookDto bookDto = new BookDto(1L, "Ведьмак", "Роман", authors, users);

        mockMvc.perform(MockMvcRequestBuilders.put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ведьмак"))
                .andExpect(jsonPath("$.genre").value("Роман"));
    }

    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/1")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with id 1 has been deleted."));
    }

    @Test
    void getAllBooksView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .with(httpBasic("admin@email.com", "11111")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("books"));
    }
}