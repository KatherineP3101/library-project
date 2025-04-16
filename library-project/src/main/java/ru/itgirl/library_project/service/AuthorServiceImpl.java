package ru.itgirl.library_project.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.repository.AuthorRepository;
import ru.itgirl.library_project.specifications.AuthorSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Fetching author by id: {}", id);
        Author author = authorRepository.findById(id).orElseThrow(() ->{
            log.warn("Author not found for id: {}", id);
            return new RuntimeException("Author not found");
        });
        log.info("Successfully fetched author by id");
        return convertToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameV1(String name) {
        log.info("Fetching author by name: {}", name);
        Author author = authorRepository.getAuthorByName(name).orElseThrow(() -> {
            log.warn("Author not found for name: {}", name);
            return new RuntimeException("Author not found");
        });
        log.info("Successfully fetched author by name");
        return convertToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameV2(String name) {
        log.info("Fetching author by name by SQL: {}", name);
        Author author = authorRepository.getAuthorByNameSql(name);
        log.info("Successfully fetched author by name (SQL query)");
        return convertToDto(author);
    }

    @Override
    public List<AuthorDto> getAuthorByNameOrSurname(String name, String surname) {
        log.info("Fetching author(s) by name: {} or surname: {}", name, surname);

        Specification<Author> specification = Specification.where(null);
        if (StringUtils.isNotEmpty(name)) {
            specification = specification.and(AuthorSpecification.hasName(name));
        }
        if (StringUtils.isNotEmpty(surname)) {
            specification = specification.and(AuthorSpecification.hasSurname(surname));
        }

        List<Author> authorList = authorRepository.findAll(specification);
        List<AuthorDto> authorDtoList = new ArrayList<>();

        for (Author author : authorList) {
            AuthorDto authorDto = convertToDto(author);
            authorDtoList.add(authorDto);
        }

        log.info("Found authors matching criteria");
        return authorDtoList;
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        log.info("Fetching all authors sorted by id");

        List<Author> authors = authorRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<AuthorDto> authorsDtoList = authors.stream().map(this::convertToDto).collect(Collectors.toList());
        log.info("Numbers of authors found: {}", authors.size());
        return authorsDtoList;
    }

    private AuthorDto convertToDto(Author author) {
        log.debug("Converting Author entity to DTO for author: {}", author.getName());

        List<BookDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build()
                ).toList();
        return AuthorDto.builder()
                .books(bookDtoList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }

}
