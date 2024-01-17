package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для задач связанных со страницей входа.
 * Оставил его для разгрузки configure в WebSecurityConfig
 * Я считаю хорошей практикой использовать его.
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}