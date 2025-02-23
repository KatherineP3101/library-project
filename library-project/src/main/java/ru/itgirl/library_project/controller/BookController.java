package ru.itgirl.library_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.service.BookService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Books", description = "Books management")
public class BookController {

    private final BookService bookService;

    @PostMapping("")
    @Operation(summary = "Create a new book", description = "This method allows to add a new book to the database.")
    public ResponseEntity<?> createNewBook(@Valid @RequestBody BookDto bookDto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                errors.put(fieldError.getField(), objectError.getDefaultMessage());
            });

            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().body(bookService.createNewBook(bookDto));
    }

    @PutMapping("")
    @Operation(summary = "Update book", description = "This method allows to update any book in the database.")
    public ResponseEntity<?> updateBook(@Valid @RequestBody BookDto bookDto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                errors.put(fieldError.getField(), objectError.getDefaultMessage());
            });

            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().body(bookService.updateBook(bookDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by id", description = "This method allows to delete the book with " +
            "the id provided from the database.")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookService.deleteBook(id));
    }

    @GetMapping("")
    @Operation(summary = "Table with all books in browser.", description = "This method allows to display all the books" +
            "as a styled table in a browser.")
    public String getAllBooksView(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "index";
    }
}
