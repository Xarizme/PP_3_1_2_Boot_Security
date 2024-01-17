package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.summer.spring.boot_security.model.User;

/**
 * Репозиторий для работы с сущностями User в базе данных.
 * Наследуется от JpaRepository для использования встроенных методов работы с базой данных.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
