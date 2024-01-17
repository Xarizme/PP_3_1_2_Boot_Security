package ru.kata.spring.boot_security.demo.service;

import ru.summer.spring.boot_security.model.Role;

/**
 * Сервисный интерфейс для работы с ролями (Role).
 */
public interface RoleService {

    Role findByName(String name);

}
