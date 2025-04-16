package ru.itgirl.library_project.service;

import ru.itgirl.library_project.dto.BookDto;

import java.util.List;


public interface BookService {

    BookDto createNewBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    String deleteBook(Long id);

    List<BookDto> findAllBooks();

}
