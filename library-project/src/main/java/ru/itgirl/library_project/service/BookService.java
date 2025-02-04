package ru.itgirl.library_project.service;

import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.model.Book;


public interface BookService {

    Book createNewBook(String name, Long genreId, Long authorId);

    Book updateBook(BookDto bookDto);

    String deleteBook(Long id);

}
