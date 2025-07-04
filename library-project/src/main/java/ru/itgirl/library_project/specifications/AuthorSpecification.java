package ru.itgirl.library_project.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.itgirl.library_project.model.Author;

public class AuthorSpecification {

    public static Specification<Author> hasName(String name) {
        Specification<Author> specification = ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), name);
        });

        return specification;
    }

    public static Specification<Author> hasSurname(String surname) {
        Specification<Author> specification = ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("surname"), surname);
        });

        return specification;
    }
}
