package ru.itgirl.library_project.service;

import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.exception.NameNotFoundInDatabaseException;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);

    AuthorDto getAuthorByNameV2(String name) throws NameNotFoundInDatabaseException;

    List<AuthorDto> getAuthorByNameV3(String name) throws NameNotFoundInDatabaseException;
}
