package ru.itgirl.library_project.service;

import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.model.Book;

import java.util.List;


public interface BookService {

    Book createNewBook(String name, Long genreId, Long authorId);

    Book updateBook(BookDto bookDto);

    String deleteBook(Long id);

    List<BookDto> findAllBooks();

}
