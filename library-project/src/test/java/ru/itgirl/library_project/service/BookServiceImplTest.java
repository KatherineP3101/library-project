package ru.itgirl.library_project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.model.Book;
import ru.itgirl.library_project.model.Genre;
import ru.itgirl.library_project.model.User;
import ru.itgirl.library_project.repository.AuthorRepository;
import ru.itgirl.library_project.repository.BookRepository;
import ru.itgirl.library_project.repository.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto bookDto;
    private Genre genre;
    private Set<User> users;
    private Set<Author> authors;

    @BeforeEach
    void setUp() {
        Set<Book> books = new HashSet<>();

        genre = new Genre(1L, "Научная фантастика", books);

        Author author = new Author(1L, "Айзек", "Азимов", books);
        authors = new HashSet<>();
        authors.add(author);

        User user = new User();
        users = new HashSet<>();
        users.add(user);

        book = new Book(1L, "Академия", genre, users, authors);
        bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setGenre(book.getGenre().getName());
    }

    @Test
    void createNewBook_Success() {
        Mockito.when(genreRepository.findByName("Научная фантастика")).thenReturn(Optional.of(genre));
        Mockito.when(authorRepository.getAuthorByName("Айзек")).thenReturn(Optional.of(authors.iterator().next()));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        BookDto result = bookService.createNewBook(bookDto);

        assertNotNull(result);
        assertEquals("Академия", result.getName());
        assertEquals("Научная фантастика", result.getGenre());
    }

    @Test
    void createNewBook_unSuccess_GenreNotFound() {
        Mockito.when(genreRepository.findByName("Научная фантастика")).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> bookService.createNewBook(bookDto));
    }

    @Test
    void updateBook_Success() {
        Mockito.when(bookRepository.getReferenceById(1L)).thenReturn(book);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        Book result = bookService.updateBook(bookDto);

        assertNotNull(result);
        assertEquals("Академия", result.getName());
    }

    @Test
    void deleteBook_Success() {
        Mockito.when(bookRepository.getReferenceById(1L)).thenReturn(book);
        Mockito.doNothing().when(bookRepository).delete(book);

        String result = bookService.deleteBook(1L);

        assertEquals("Book with id 1 has been deleted.", result);
    }

    @Test
    void deleteBook_unSuccess() {
        Mockito.when(bookRepository.getReferenceById(1L)).thenReturn(book);
        Mockito.doThrow(new RuntimeException()).when(bookRepository).delete(book);

        String result = bookService.deleteBook(1L);

        assertEquals("Book with id 1 couldn't be deleted.", result);
    }

    @Test
    void findAllBooks_Success() {
        Mockito.when(bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(book));

        List<BookDto> result = bookService.findAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Академия", result.get(0).getName());
    }
}