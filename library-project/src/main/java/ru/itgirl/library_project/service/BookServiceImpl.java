package ru.itgirl.library_project.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
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
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book createNewBook(String name, Long genreId, Long authorId) {
        Genre genre = genreRepository.getReferenceById(genreId);
        Set<Author> author = authorRepository.findById(authorId).stream().collect(Collectors.toSet());
        Book book = new Book();
        book.setName(name);
        book.setGenre(genre);
        book.setAuthors(author);

        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(BookDto bookDto) {
        Book book = bookRepository.getReferenceById(bookDto.getId());

        book.setName(StringUtils.isNotEmpty(bookDto.getName()) ? bookDto.getName() : book.getName());
        if (StringUtils.isNotEmpty(bookDto.getGenre())) {
            book.setGenre((Genre) genreRepository.findAll().stream()
                    .map(genre -> Genre.builder()
                            .name(bookDto.getGenre())
                            .build()));
        }

        return bookRepository.save(book);
    }

    @Override
    public String deleteBook(Long id) {

        Book book = bookRepository.getReferenceById(id);
        try {
            bookRepository.delete(book);
            return "Book with id " + id + "has been deleted.";
        } catch (Exception e) {
            return "Book with id " + id + "couldn't be deleted.";
        }
    }

    @Override
    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<BookDto> bookDtoList = books.stream().map(this::convertToDto).collect(Collectors.toList());
        return bookDtoList;
    }

    private BookDto convertToDto (Book book) {

        return BookDto.builder()
                .id(book.getId())
                .authors(convertAuthorListToDto(book.getAuthors()))
                .name(book.getName())
                .genre(book.getGenre().toString())
                .build();

    }

    private List<AuthorDto> convertAuthorListToDto(Set<Author> authorSet) {
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
