package ru.itgirl.library_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.itgirl.library_project.model.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    Optional<Author> getAuthorByName(String name);

    @Query("Select name, surname, books from Author where name = ?")
    Author getAuthorByNameSql(String name);

}
