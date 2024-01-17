package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.summer.spring.boot_security.model.Role;
import ru.summer.spring.boot_security.repository.RoleRepository;

/**
 * Имплементация сервиса для работы с ролями (Role).
 * Реализует интерфейс RoleService.
 */
@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
