package ru.itgirl.library_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
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
public class AuthorDto {
    private Long id;
    @NotBlank(message = "Enter author's name")
    private String name;
    @NotBlank(message = "Enter author's surname")
    private String surname;

    private List<BookDto> books;
}
