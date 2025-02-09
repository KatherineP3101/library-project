package ru.itgirl.library_project.model;

import jakarta.persistence.*;
import lombok.*;
import ru.itgirl.library_project.security.Role;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "user_book",
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<Book> books;
}
