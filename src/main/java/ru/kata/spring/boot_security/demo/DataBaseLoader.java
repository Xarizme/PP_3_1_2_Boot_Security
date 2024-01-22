package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.HashSet;
import java.util.Set;

@Component
public class DataBaseLoader implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DataBaseLoader(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Role adminrole = new Role("ROLE_ADMIN");
        Role userrole = new Role("ROLE_USER");

        this.roleRepository.save(adminrole);
        this.roleRepository.save(userrole);

        Set<Role> adminRole = new HashSet<>(Set.of(adminrole,userrole));
        Set<Role> userRole = new HashSet<>(Set.of(userrole));
        Set<Role> oneMoreUser = new HashSet<>(Set.of(adminrole));


        User admin = new User("admin", "Admin", 25, "admin@yandex.ru", passwordEncoder.encode("admin"), adminRole);


        User user = new User("user", "User", 20, "user@yandex.ru", passwordEncoder.encode("user"), userRole);
        User oneMore = new User("Valera", "User", 35, "user@mail.ru", passwordEncoder.encode("valera"), oneMoreUser);

        this.userRepository.save(admin);
        this.userRepository.save(user);
        this.userRepository.save(oneMore);
    }
}
