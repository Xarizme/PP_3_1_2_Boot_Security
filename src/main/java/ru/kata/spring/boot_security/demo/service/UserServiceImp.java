package ru.kata.spring.boot_security.demo.service;


import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImp(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public User showUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public void update(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

    public String getUserRole(User user) {
        return user.getRoles().stream()
                .map(Role ::getName).map(roleName -> roleName.replace("ROLE_", ""))
                .sorted()
                .collect(Collectors.joining(" "));
    }
}
