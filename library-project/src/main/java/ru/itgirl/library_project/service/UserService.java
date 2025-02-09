package ru.itgirl.library_project.service;


import org.springframework.stereotype.Service;
import ru.itgirl.library_project.dto.UserDto;
import ru.itgirl.library_project.model.Book;
import ru.itgirl.library_project.model.User;

import java.util.List;

@Service
public interface UserService {
    UserDto createUser(User user);
    List<User> getAllUsers();
    User updateUser(UserDto userDto);
    String deleteUser(Long id);

    List<Book> getUserBooks(Long id);
}
