package ru.itgirl.library_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private Long id;
    @NotBlank(message = "Enter book's title")
    private String name;
    @NotBlank(message = "Enter book's genre")
    private String genre;

    @NotNull(message = "Enter book's author(s)")
    private List<AuthorDto> authors;
    private List<UserDto> users;
}
