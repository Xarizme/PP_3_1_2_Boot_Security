package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void save(User user);
    void deleteById(Long id);
    String getUserRole (User user);

    User showUserById(Long id);

    void update(Long id, User user);

    User findByEmail (String email);

}
