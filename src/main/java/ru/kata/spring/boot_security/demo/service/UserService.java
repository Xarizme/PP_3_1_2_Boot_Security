package ru.kata.spring.boot_security.demo.service;

import ru.summer.spring.boot_security.model.User;

import java.util.List;
import java.util.Map;

/**
 * Сервисный интерфейс для работы с пользователями (User).
 */
public interface UserService {


    void delete(Long id);

    Map<User, String> getAllUsersWithRoles();

    String getUserRoles(User user);

    void addUserWithRoles(List<String> roles, User user);

    void updateUserWithRoles(User user, List<String> roles);

    User findById(Long id);

    User findByEmail(String email);

}
