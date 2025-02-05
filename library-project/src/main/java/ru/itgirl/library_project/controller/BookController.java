package ru.itgirl.library_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.model.Book;
import ru.itgirl.library_project.model.Genre;
import ru.itgirl.library_project.repository.BookRepository;
import ru.itgirl.library_project.service.BookService;

import java.util.Set;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("")
    public Book createNewBook(String name, Long genreId, Long authorId) {
        return bookService.createNewBook(name, genreId, authorId);
    }

    @PutMapping("")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok().body(bookService.updateBook(bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookService.deleteBook(id));
    }

    @GetMapping("/books")
    public String getAllBooksView(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "index";
    }
}
