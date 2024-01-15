package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servies.UserService;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class AdminUserController {
    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String printAllUsers(Model model) {

        Map<User,String> allUsers = userService
                .getAllUsers()
                .stream()
                .sorted(Comparator.comparing(s-> s.getId()))
                .collect(Collectors.toMap(Function.identity(),
                        userService::getUserRoles,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        model.addAttribute("allUsers", allUsers);
        return "adminPage";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@RequestParam String username,
                          @RequestParam String lastname,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String role) {
        User user = new User(username, lastname, email,password);
        userService.add(user);
        userService.addRoleToUser(role,user);

        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/update")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String username,
                             @RequestParam String lastname,
                             @RequestParam String email,
                             @RequestParam String password) {
        User user = userService.findById(id);

        if (user != null) {
            user.setUsername(username);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setPassword(password);
            userService.update(user);
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete")
    public String deleteUserById(@RequestParam Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
