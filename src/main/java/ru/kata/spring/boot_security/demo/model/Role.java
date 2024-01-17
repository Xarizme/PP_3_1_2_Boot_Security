package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Сущность, представляющая роль пользователя в системе безопасности.
 * Реализует интерфейс GrantedAuthority для интеграции с Spring Security.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование роли. Уникальное в системе.
     */
    @Column(name = "role_name", unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    /**
     * Возвращает наименование роли как разрешение (authority) в Spring Security.
     */
    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
