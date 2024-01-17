package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.summer.spring.boot_security.model.Role;
import ru.summer.spring.boot_security.model.User;
import ru.summer.spring.boot_security.repository.UserRepository;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Имплементация сервиса для работы с пользователями (User).
 * Реализует интерфейсы UserService и UserDetailsService.
 */
@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    public UserServiceImp(UserRepository userRepository,
                          RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    /**
     * Удаляет пользователя по его ID.
     */
    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Получает всех пользователей с их ролями, упорядоченных по ID.
     */
    @Transactional(readOnly = true)
    @Override
    public Map<User, String> getAllUsersWithRoles() {
        return userRepository
                .findAll(Sort.by("id"))
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toMap(
                        Function.identity(),
                        this::getUserRoles,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    /**
     * Находит пользователя по email.
     */
    @Transactional(readOnly = true)
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Загружает данные пользователя по логину пользователя(email).
     * Этот метод предназначен для использования Spring Security.
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);
    }

    /**
     * Получает роли пользователя, удаляет префикс "ROLE_" из каждой роли,
     * сортирует роли и объединяет их в одну строку, разделенную пробелами.
     */
    @Transactional(readOnly = true)
    @Override
    public String getUserRoles(User user) {
        return user.getRoles().stream()
                .map(Role::getName)
                .map(roleName -> roleName.replace("ROLE_", ""))
                .sorted()
                .collect(Collectors.joining(" "));
    }

    /**
     * Добавляет пользователя с заданными ролями.
     */
    @Transactional
    @Override
    public void addUserWithRoles(List<String> roles, User user) {
        for (String roleName : roles) {
            Role role = roleService.findByName(roleName);
            user.getRoles().add(role);
        }
        userRepository.save(user);
    }

    /**
     * Обновляет пользователя и его роли.
     */
    @Transactional
    @Override
    public void updateUserWithRoles(User user, List<String> roles) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setAge(user.getAge());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());

            existingUser.getRoles().clear();

            for (String roleName : roles) {
                Role role = roleService.findByName(roleName);
                existingUser.getRoles().add(role);
            }

            userRepository.save(existingUser);
        }
    }

    /**
     * Находит пользователя по его ID.
     */
    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
