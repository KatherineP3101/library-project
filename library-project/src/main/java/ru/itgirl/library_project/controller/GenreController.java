package ru.itgirl.library_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgirl.library_project.dto.GenreDto;
import ru.itgirl.library_project.service.GenreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
@Tag(name = "Genres", description = "Genres management")
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/{id}")
    @Operation(summary = "Obtain genre by id", description = "This method returns the genre under the provided id number.")
    GenreDto getGenreById(@PathVariable("id") Long id) {
        return genreService.getGenreById(id);
    }

}
