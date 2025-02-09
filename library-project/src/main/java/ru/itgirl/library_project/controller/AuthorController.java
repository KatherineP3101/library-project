package ru.itgirl.library_project.controller;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping()
    public String getAllAuthors(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String surname,
                                Model model) {
        if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(surname)) {
            return authorService.getAuthorByNameOrSurname(name, surname).toString();
        } else {
            List<AuthorDto> authors = authorService.getAllAuthors();
            model.addAttribute("Authors", authors);
            return "authorsTable";
        }
    }

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
    List<AuthorDto> getAuthorByNameSpec(@RequestParam("name") String name,
                                        @RequestParam("surname") String surname) {
        return authorService.getAuthorByNameOrSurname(name, surname);
    }

}
