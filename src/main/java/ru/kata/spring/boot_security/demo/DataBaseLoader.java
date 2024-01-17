package ru.kata.spring.boot_security.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Component
public class DataBaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DataBaseLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        Role adminrole = new Role("ROLE_ADMIN");
        Role userrole = new Role("ROLE_USER");

        this.roleRepository.save(adminrole);
        this.roleRepository.save(userrole);

        User admin = new User("admin", "Admin", "admin@yandex.ru", "admin");
        admin.setRoles(new HashSet<>(List.of(adminrole,userrole)));

        User user = new User("user", "User", "user@yandex.ru", "user");
        user.setRoles(new HashSet<>(List.of(userrole)));

        this.userRepository.save(admin);
        this.userRepository.save(user);

    }
}
