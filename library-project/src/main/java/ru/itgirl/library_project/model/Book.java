package ru.itgirl.library_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany(mappedBy = "books")
    private Set<User> users;

    @ManyToMany
    @JoinTable(
            name = "author_book",
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    private Set<Author> authors;

}
