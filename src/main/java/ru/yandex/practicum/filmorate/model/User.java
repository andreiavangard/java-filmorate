package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class User {
    //заполняем автоматически
    private Long id;
    @NotBlank(message = "Адрес почты должен быть указан")
    @Email(message = "Неправильный формат электронной почты")
    private String email;
    @NotBlank(message = "Логин должен быть указан")
    private String login;
    //имя может быть пустым, тогда берется из логина
    private String name;
    @NotNull(message = "День рождения должен быть указан")
    @Past(message = "День рождения должен быть в прошлом")
    private LocalDate birthday;

}