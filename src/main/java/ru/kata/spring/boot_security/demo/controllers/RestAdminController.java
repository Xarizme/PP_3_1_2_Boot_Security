package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/adminApi")
public class RestAdminController {

    private final UserService userService;

    @Autowired
    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUser")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/oneUser/{id}")
    public User getOneUSer(@PathVariable Long id) {
        return userService.showUserById(id);
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<HttpStatus> editUser(@RequestBody User user,
                                               @PathVariable Long id) {
        userService.update(id, user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
