package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.summer.spring.boot_security.dto.UserDto;
import ru.summer.spring.boot_security.model.User;
import ru.summer.spring.boot_security.service.UserService;
import ru.summer.spring.boot_security.util.UserMapper;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для административных задач пользователя.
 * Доступен только для пользователей с ролью ADMIN.
 */
@Controller
public class UserAdminController {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserAdminController(UserService userService,
                               UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Выводит список всех пользователей
     * и текущего пользователя кто вошел в систему.
     */
    @GetMapping(value = "/admin")
    public String printUsersAndCurrentUser(Model model, Principal principal) {
        User currentUser = userService.findByEmail(principal.getName());
        String userRoles = userService.getUserRoles(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userRoles", userRoles);

        Map<User, String> usersWithRoles = userService.getAllUsersWithRoles();
        model.addAttribute("usersWithRoles", usersWithRoles);

        return "adminPage";
    }

    /**
     * Добавляет нового пользователя.
     */
    @PostMapping(value = "/admin/add")
    public String addUser(@ModelAttribute UserDto userDto, @RequestParam List<String> role) {
        User user = userMapper.toModel(userDto);
        userService.addUserWithRoles(role, user);

        return "redirect:/admin";
    }

    /**
     * Обновляет существующего пользователя.
     */
    @PostMapping(value = "/admin/update")
    public String updateUser(@ModelAttribute UserDto userDto,
                             @RequestParam List<String> role) {
        User user = userMapper.toModel(userDto);
        userService.updateUserWithRoles(user, role);

        return "redirect:/admin";
    }

    /**
     * Удаляет пользователя.
     */
    @PostMapping(value = "/admin/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.delete(id);

        return "redirect:/admin";
    }
}
