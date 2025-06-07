package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.debug("Получили список пользователей");
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        log.debug("Начало добавления пользователя {}", user);
        if (validate(user)) {
            user.setId(Service.getNextId(users));
            if (user.getName() == null) {
                user.setName(user.getLogin());
                log.debug("Для пользователя в качестве имени указан логин");
            }
            users.put(user.getId(), user);
        }
        log.debug("Добавлен пользователь {}", user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        if (users.containsKey(newUser.getId())) {
            log.debug("Начало обновления пользователя {}", newUser);
            User oldUser = users.get(newUser.getId());
            oldUser.setName(newUser.getName());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setBirthday(newUser.getBirthday());
            oldUser.setBirthday(newUser.getBirthday());
            log.debug("Окончание обновления пользователя {}", oldUser);
            return oldUser;
        }
        log.error("Пользователь с id = {} не найден", newUser.getId());
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    public static boolean validate(User user) {
        String textExp = "";
        if (user.getEmail().isBlank()) {
            log.error("Электронный адрес не может быть пустым, пользователь {}", user);
            throw new ValidationException("Электронный адрес не может быть пустым");
        }
        if (!user.getEmail().contains("@")) {
            log.error("Неправильный формат электронного адреса, пользователь {}", user);
            throw new ValidationException("Неправильный формат электронного адреса");
        }
        if (user.getLogin().isBlank()) {
            log.error("Логин не может быть пустым, пользователь {}", user);
            throw new ValidationException("Логин не может быть пустым");
        }
        if (user.getLogin().contains(" ")) {
            log.error("Логин не может содержать пробелы, пользователь {}", user);
            throw new ValidationException("Логин не может содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения не может быть в будущем, пользователь {}", user);
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        return true;
    }

}