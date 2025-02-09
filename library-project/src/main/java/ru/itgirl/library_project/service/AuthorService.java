package ru.itgirl.library_project.service;

import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.model.Author;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);

    AuthorDto getAuthorByNameV2(String name);

    List<AuthorDto> getAuthorByNameOrSurname(String name, String surname);

    List<AuthorDto> getAllAuthors();
}
