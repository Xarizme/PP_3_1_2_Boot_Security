package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, представляющая пользователя в системе.
 * Реализует интерфейс UserDetails для интеграции с Spring Security.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private Integer age;

    @Column(unique = true)
    private String email;

    private String password;

    /**
     * Состояние активации пользователя.
     */
    @ToString.Exclude
    private boolean enabled;

    /**
     * Роли, присвоенные пользователю.
     */
    @ManyToMany
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    public User(String firstname,
                String lastName,
                String email,
                Integer age,
                String password) {
        this.firstname = firstname;
        this.lastname = lastName;
        this.email = email;
        this.age = age;
        this.password = password;
        this.enabled = true;
    }

    /**
     * Возвращает коллекцию ролей, связанных с этим пользователем.
     * Этот метод необходим для интеграции с Spring Security.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Метод возвращает email пользователя. (Login)
     * Этот метод необходим для интеграции с Spring Security.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Метод возвращает пароль пользователя. (Password)
     * Этот метод необходим для интеграции с Spring Security.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Проверяет, не истек ли срок действия учетной записи пользователя.
     * Данную функцию я не реализовывал,
     * true, если срок действия учетной записи не истек
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет, не заблокирована ли учетная запись пользователя.
     * Данную функцию я не реализовывал,
     * true, если учетная запись не заблокирована
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет, не истекли ли учетные данные пользователя.
     * Данную функцию я не реализовывал,
     * true, если учетные данные не истекли
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет, активирован ли пользователь.
     * При создании User пользователь активирован по умолчанию.
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
