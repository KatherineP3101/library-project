package ru.itgirl.library_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.library_project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
