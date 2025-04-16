package ru.itgirl.library_project.controller;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.service.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
@Tag(name = "Authors", description = "Authors management")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping()
    @Operation(summary = "Get the list of all authors", description = "This method allows to get the list of all" +
            "the authors in the database.")
    public String getAllAuthors(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String surname,
                                Model model) {
        if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(surname)) {
            return authorService.getAuthorByNameOrSurname(name, surname).toString();
        } else {
            List<AuthorDto> authors = authorService.getAllAuthors();
            model.addAttribute("authors", authors);
            return "authorsTable";
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get author by id", description = "This method allows to get the author by the id provided.")
    ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(authorService.getAuthorById(id));
    }

    @GetMapping("/name")
    @Operation(summary = "Get author by name", description = "This method allows to get all authors with the name provided.")
    ResponseEntity<AuthorDto> getAuthorByName(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(authorService.getAuthorByNameV1(name));
    }

    @GetMapping("/name/sql")
    @Operation(summary = "Get author by name (as SQL)", description = "This method allows to get all authors with " +
            "the name provided, by creating an SQL query.")
    ResponseEntity<AuthorDto> getAuthorByNameSql(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(authorService.getAuthorByNameV2(name));
    }

    @GetMapping("/name/spec")
    @Operation(summary = "Get author by name or surname", description = "This method allows to get all authors " +
            "with the name and/or surname provided by following the AuthorSpecification method.")
    List<AuthorDto> getAuthorByNameSpec(@RequestParam("name") String name,
                                        @RequestParam("surname") String surname) {
        return authorService.getAuthorByNameOrSurname(name, surname);
    }

}
