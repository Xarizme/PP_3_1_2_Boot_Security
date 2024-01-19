package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);

    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);

    }

    @Override
    public Role showUserById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
