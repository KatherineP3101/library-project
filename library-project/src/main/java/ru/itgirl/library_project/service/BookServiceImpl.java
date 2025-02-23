package ru.itgirl.library_project.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.model.Book;
import ru.itgirl.library_project.model.Genre;
import ru.itgirl.library_project.repository.AuthorRepository;
import ru.itgirl.library_project.repository.BookRepository;
import ru.itgirl.library_project.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookDto createNewBook(BookDto bookDto) {
        log.info("Creating new book: {}", bookDto.getName());
        Genre genre = genreRepository.findByName(bookDto.getGenre())
                .orElseThrow(() -> {
                    log.warn("Genre not found: {}", bookDto.getGenre());
                    return new RuntimeException("Genre not found");
                });
        Set<Author> authors = bookDto.getAuthors().stream()
                .map(auth ->  authorRepository.getAuthorByName(auth.getName()).orElseThrow())
                .collect(Collectors.toSet());
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setGenre(genre);
        book.setAuthors(authors);

        log.info("Book created successfully");
        bookRepository.save(book);
        return convertToDto(book);
    }

    @Override
    public Book updateBook(BookDto bookDto) {
        log.info("Updating book: {}", bookDto.getName());
        Book book = bookRepository.getReferenceById(bookDto.getId());

        book.setName(StringUtils.isNotEmpty(bookDto.getName()) ? bookDto.getName() : book.getName());
        if (StringUtils.isNotEmpty(bookDto.getGenre())) {
            book.setGenre((Genre) genreRepository.findAll().stream()
                    .map(genre -> Genre.builder()
                            .name(bookDto.getGenre())
                            .build()));
        }

        log.info("Book updated successfully");
        return bookRepository.save(book);
    }

    @Override
    public String deleteBook(Long id) {
        log.info("Deleting book with id: {}", id);
        Book book = bookRepository.getReferenceById(id);
        try {
            bookRepository.delete(book);
            log.info("Book with ID {} has been successfully deleted.", id);
            return "Book with id " + id + "has been deleted.";
        } catch (Exception e) {
            log.error("Couldn't delete book with ID: {}", id, e);
            return "Book with id " + id + "couldn't be deleted.";
        }
    }

    @Override
    public List<BookDto> findAllBooks() {
        log.info("Fetching all books sorted by id");
        List<Book> books = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        log.info("Total books found: {}", books.size());

        List<BookDto> bookDtoList = books.stream().map(this::convertToDto).collect(Collectors.toList());
        return bookDtoList;
    }

    private BookDto convertToDto (Book book) {
        log.debug("Converting Book entity to DTO: {}", book.getName());

        return BookDto.builder()
                .id(book.getId())
                .authors(convertAuthorListToDto(book.getAuthors()))
                .name(book.getName())
                .genre(book.getGenre().getName())
                .build();

    }

    private List<AuthorDto> convertAuthorListToDto(Set<Author> authorSet) {
        log.debug("Converting {} authors to DTO", authorSet.size());
        List<AuthorDto> authorDtoList = new ArrayList<>();
        for (Author author : authorSet) {
            AuthorDto authorDto = AuthorDto.builder()
                    .id(author.getId())
                    .name(author.getName())
                    .surname(author.getSurname())
                    .build();
            authorDtoList.add(authorDto);
        }
        return authorDtoList;
    }

}
