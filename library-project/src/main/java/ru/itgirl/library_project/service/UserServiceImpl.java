package ru.itgirl.library_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
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
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(User user) {
        String passwordEncode = (passwordEncoder.encode(user.getPassword()));
        user.setPassword(passwordEncode);
        User newUser = userRepository.save(user);
        UserDto newUserDto = new ObjectMapper().convertValue(newUser, UserDto.class);
        return newUserDto;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = userRepository.getReferenceById(userDto.getId());

        user.setUsername(StringUtils.isNotEmpty(userDto.getUsername()) ? userDto.getUsername() : user.getUsername());
        user.setEmail(StringUtils.isNotEmpty(userDto.getEmail()) ? userDto.getEmail() : user.getEmail());
        return user;
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.getReferenceById(id);
        try {
            userRepository.delete(user);
            return "User with id " + id + "has been deleted.";
        } catch (Exception e) {
            return "User with id " + id + "couldn't be deleted.";
        }
    }

    @Override
    public List<Book> getUserBooks(Long id) {
        User user = userRepository.getReferenceById(id);
        List<Book> bookList = user.getBooks().stream().toList();
        return bookList;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
