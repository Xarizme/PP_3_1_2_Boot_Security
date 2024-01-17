package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.summer.spring.boot_security.model.User;
import ru.summer.spring.boot_security.service.UserService;

import java.security.Principal;

/**
 * Контроллер для задач, связанных с пользователями.
 * Доступен для ролей USER и ADMIN
 */
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает страницу пользователя.
     * Позволяет получить данные о текущем пользователе
     */
    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        User currentUser = userService.findByEmail(principal.getName());
        String userRoles = userService.getUserRoles(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userRoles", userRoles);
        return "userPage";
    }

    /**
     * Отображает страницу пользователя по идентификатору.
     * Доступен только для пользователей с ролью ADMIN
     * p.s. Этот метод оставлен с прошлой таски, он в этой задаче не реализуется
     */
    @GetMapping("/user/{id}")
    public String getUserPageById(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "userPage";
    }

}
