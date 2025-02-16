package ru.itgirl.library_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.library_project.dto.UserDto;
import ru.itgirl.library_project.model.Book;
import ru.itgirl.library_project.model.User;
import ru.itgirl.library_project.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public UserDto addNewUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public List<Book> getUserBooks(@PathVariable("id") Long id){
        return userService.getUserBooks(id);
    }

}
