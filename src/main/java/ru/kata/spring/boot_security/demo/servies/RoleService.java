package ru.kata.spring.boot_security.demo.servies;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService {

    Role findByName(String name);
}
