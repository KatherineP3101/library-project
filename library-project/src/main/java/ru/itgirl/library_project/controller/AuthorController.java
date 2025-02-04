package ru.itgirl.library_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/name")
    AuthorDto getAuthorByName(@RequestParam("name") String name) {
        return authorService.getAuthorByNameV1(name);
    }

    @GetMapping("/name/sql")
    AuthorDto getAuthorByNameSql(@RequestParam("name") String name) {
        return authorService.getAuthorByNameV2(name);
    }

    @GetMapping("/name/spec")
    List<AuthorDto> getAuthorByNameSpec(@RequestParam("name") String name) {
        return authorService.getAuthorByNameV3(name);
    }

}
