package ru.itgirl.library_project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.model.Book;
import ru.itgirl.library_project.repository.AuthorRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        Set<Book> books = new HashSet<>();
        List<BookDto> booksList = new ArrayList<>();
        author = new Author(12L, "Жюль1", "Верн", books);
    }

    @Test
    void getAuthorById_Success() {
        Mockito.when(authorRepository.findById(12L)).thenReturn(Optional.of(author));

        AuthorDto authorResult = authorService.getAuthorById(12L);
        assertNotNull(authorResult);
        assertEquals("Жюль", author.getName());
        assertEquals("Верн", author.getSurname());
    }

    @Test
    void getAuthorById_unSuccess() {
        Mockito.when(authorRepository.findById(10L)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> authorService.getAuthorById(10L));
    }

    @Test
    void getAuthorByNameV1_Success() {
        Mockito.when(authorRepository.getAuthorByName("Жюль1")).thenReturn(Optional.of(author));

        AuthorDto authorResult = authorService.getAuthorByNameV1("Жюль1");
        assertNotNull(authorResult);
        assertEquals("Жюль", authorResult.getName());
        assertEquals("Верн", authorResult.getSurname());
    }

    @Test
    void getAuthorByNameV1_unSuccess() {
        Mockito.when(authorRepository.getAuthorByName("Неизвестный")).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> authorService.getAuthorByNameV1("Неизвестный"));
    }

    @Test
    void getAuthorByNameV2_Success() {
        Mockito.when(authorRepository.getAuthorByNameSql("Жюль")).thenReturn(author);

        AuthorDto authorResult = authorService.getAuthorByNameV2("Жюль");
        assertNotNull(authorResult);
        assertEquals("Жюль", authorResult.getName());
        assertEquals("Верн", authorResult.getSurname());
    }

    @Test
    void getAuthorByNameOrSurname_SuccessByName() {
        Mockito.when(authorRepository.findAll(Mockito.any(Specification.class))).thenReturn(List.of(author));

        List<AuthorDto> authorResults = authorService.getAuthorByNameOrSurname("Жюль", "");
        assertNotNull(authorResults);
        assertEquals(1, authorResults.size());
        assertEquals("Жюль", authorResults.get(0).getName());
    }

    @Test
    void getAuthorByNameOrSurname_SuccessBySurname() {
        Mockito.when(authorRepository.findAll(Mockito.any(Specification.class))).thenReturn(List.of(author));

        List<AuthorDto> authorResults = authorService.getAuthorByNameOrSurname("Жюль", "Верн");
        assertNotNull(authorResults);
        assertEquals(1, authorResults.size());
        assertEquals("Верн", authorResults.get(0).getSurname());
    }

    @Test
    void getAuthorByNameOrSurname_Success() {
        Mockito.when(authorRepository.findAll(Mockito.any(Specification.class))).thenReturn(List.of(author));

        List<AuthorDto> authorResults = authorService.getAuthorByNameOrSurname("", "Верн");
        assertNotNull(authorResults);
        assertEquals(1, authorResults.size());
        assertEquals("Жюль", authorResults.get(0).getName());
        assertEquals("Верн", authorResults.get(0).getSurname());
    }

    @Test
    void getAllAuthors_Success() {
        Mockito.when(authorRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(author));

        List<AuthorDto> authorResults = authorService.getAllAuthors();
        assertNotNull(authorResults);
        assertEquals(1, authorResults.size());
        assertEquals("Жюль", authorResults.get(0).getName());
    }
}