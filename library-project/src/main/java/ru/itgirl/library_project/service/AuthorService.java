package ru.itgirl.library_project.service;

import ru.itgirl.library_project.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);

    AuthorDto getAuthorByNameV2(String name);

    List<AuthorDto> getAuthorByNameV3(String name);
}
