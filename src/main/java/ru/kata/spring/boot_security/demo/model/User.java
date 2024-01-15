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

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String lastname;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String password;

    private boolean enabled;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();



    public User(String username, String lastName, String email, String password) {
        this.username = username;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.enabled = true;
    }

    /**
     * Возвращает коллекцию ролей, связанных с этим пользователем.
     * Этот метод необходим для интеграции с Spring Security.
     *
     * @return коллекция объектов, каждый из которых реализует GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }

}