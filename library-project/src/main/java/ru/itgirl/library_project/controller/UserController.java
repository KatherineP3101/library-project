package ru.itgirl.library_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.library_project.dto.UserDto;
import ru.itgirl.library_project.model.Book;
import ru.itgirl.library_project.model.User;
import ru.itgirl.library_project.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "Users management")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @Operation(summary = "Create a new user", description = "This method allows to add a new user to the database.")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                errors.put(fieldError.getField(), objectError.getDefaultMessage());
            });

            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the list of user's books", description = "This method allows to get the books from " +
            "the user with the indicated id number.")
    public List<Book> getUserBooks(@PathVariable("id") Long id){
        return userService.getUserBooks(id);
    }

}
