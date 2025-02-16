package ru.itgirl.library_project.controller;

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
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("")
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
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookService.deleteBook(id));
    }

    @GetMapping("/books")
    public String getAllBooksView(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "index";
    }
}
