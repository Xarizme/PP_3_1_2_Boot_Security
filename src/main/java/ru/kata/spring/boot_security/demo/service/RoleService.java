package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void save (Role role);

    void deleteById(Long id);

    Role showUserById(Long id);

}
