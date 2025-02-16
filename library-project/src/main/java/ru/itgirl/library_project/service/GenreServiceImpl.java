
package ru.itgirl.library_project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itgirl.library_project.dto.AuthorDto;
import ru.itgirl.library_project.dto.BookDto;
import ru.itgirl.library_project.dto.GenreDto;
import ru.itgirl.library_project.model.Author;
import ru.itgirl.library_project.model.Genre;
import ru.itgirl.library_project.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreDto getGenreById(Long id) {
        log.info("Fetching genre by id: {}", id);
        Genre genre = genreRepository.findById(id).orElseThrow(() -> {
            log.warn("Genre not found with id: {}", id);
            return new RuntimeException("Genre not found");
        });
        log.info("Genre found: {}", genre.getName());
        return convertGenreToDto(genre);
    }

    private GenreDto convertGenreToDto(Genre genre) {
        log.debug("Converting Genre entity to DTO: {}", genre.getName());
        List<BookDto> bookDtoList = genre.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .authors(convertAuthorListToDto(book.getAuthors()))
                        .name(book.getName())
                        .id(book.getId())
                        .build()
                ).toList();

        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .books(bookDtoList)
                .build();
    }

    private List<AuthorDto> convertAuthorListToDto(Set<Author> authorSet) {
        log.debug("Converting {} authors to DTO", authorSet.size());
        List<AuthorDto> authorDtoList = new ArrayList<>();
        for (Author author : authorSet) {
            AuthorDto authorDto = AuthorDto.builder()
                    .id(author.getId())
                    .name(author.getName())
                    .surname(author.getSurname())
                    .build();
            authorDtoList.add(authorDto);
        }
        return authorDtoList;
    }
}
