package ru.itgirl.library_project.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.repository.AuthorRepository;
import ru.itgirl.library_project.specifications.AuthorSpecification;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameV1(String name) {
        Author author = authorRepository.getAuthorByName(name).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameV2(String name) {
        Author author = authorRepository.getAuthorByNameSql(name);
        return convertToDto(author);
    }

    @Override
    public List<AuthorDto> getAuthorByNameOrSurname(String name, String surname) {

        Specification specification = Specification.where(null);
        if (StringUtils.isNotEmpty(name)) {
            specification = specification.and(AuthorSpecification.hasName(name));
        }
        if (StringUtils.isNotEmpty(name)) {
            specification = specification.and(AuthorSpecification.hasSurname(surname));
        }

        return authorRepository.findAll(specification);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorsDtoList = authors.stream().map(this::convertToDto).collect(Collectors.toList());
        return authorsDtoList;
    }

    private AuthorDto convertToDto(Author author) {
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
