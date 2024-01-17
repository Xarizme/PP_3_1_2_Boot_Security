package ru.kata.spring.boot_security.demo.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.summer.spring.boot_security.dto.UserDto;
import ru.summer.spring.boot_security.model.User;

/**
 * Класс UserMapper используется для преобразования объектов
 * типа UserDto в объекты типа User и наоборот.
 * Для преобразования используется библиотека ModelMapper,
 * которая автоматически сопоставляет поля с одинаковыми именами.
 */
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toModel(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}