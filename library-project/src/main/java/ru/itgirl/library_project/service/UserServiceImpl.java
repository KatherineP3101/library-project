package ru.itgirl.library_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itgirl.library_project.dto.UserDto;
import ru.itgirl.library_project.model.Book;
import ru.itgirl.library_project.model.User;
import ru.itgirl.library_project.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(User user) {
        log.info("Creating new user: {}", user.getUsername());
        String passwordEncode = (passwordEncoder.encode(user.getPassword()));
        user.setPassword(passwordEncode);
        User newUser = userRepository.save(user);
        log.info("User created with ID: {}", newUser.getId());

        UserDto newUserDto = new ObjectMapper().convertValue(newUser, UserDto.class);
        return newUserDto;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users sorted by id");
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public User updateUser(UserDto userDto) {
        log.info("Updating user: {}", userDto.getUsername());
        User user = userRepository.getReferenceById(userDto.getId());

        user.setUsername(StringUtils.isNotEmpty(userDto.getUsername()) ? userDto.getUsername() : user.getUsername());
        user.setEmail(StringUtils.isNotEmpty(userDto.getEmail()) ? userDto.getEmail() : user.getEmail());
        log.info("User {} updated successfully", user.getUsername());
        return user;
    }

    @Override
    public String deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        User user = userRepository.getReferenceById(id);
        try {
            userRepository.delete(user);
            log.info("User with id: {} deleted successfully", id);
            return "User with id " + id + "has been deleted.";
        } catch (Exception e) {
            log.error("Failed to delete user with ID: {}. Error: {}", id, e.getMessage());
            return "User with id " + id + "couldn't be deleted.";
        }
    }

    @Override
    public List<Book> getUserBooks(Long id) {
        log.info("Fetching books' list for user with id: {}", id);
        User user = userRepository.getReferenceById(id);
        List<Book> bookList = user.getBooks().stream().toList();
        log.debug("User with id {} has {} books", id, bookList.size());
        return bookList;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading user by email: {}", email);
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            log.error("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        log.info("User found: {}", user.getUsername());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
