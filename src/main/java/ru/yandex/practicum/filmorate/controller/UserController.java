package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserStorage userStorage, UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public Collection<User> findAll() {
        log.debug("Получили список пользователей");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("{id}/friends")
    public Collection<User> findFriends(@PathVariable Long id) {
        return userService.findFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Collection<User> findCommonFriends(
            @PathVariable Long id,
            @PathVariable Long otherId
    ) {
        return userService.findCommonFriends(id, otherId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("Начало добавления пользователя {}", user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.debug("Для пользователя в качестве имени указан логин");
        }
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        if (userService.getMapUsers().containsKey(newUser.getId())) {
            log.debug("Начало обновления пользователя {}", newUser);
            return userService.update(newUser);
        }
        log.error("Пользователь с id = {} не найден", newUser.getId());
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    @PutMapping("{id}/friends/{friendId}")
    public User addFriend(
            @PathVariable Long id,
            @PathVariable Long friendId
    ) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public User deleteFriend(
            @PathVariable Long id,
            @PathVariable Long friendId
    ) {
        return userService.deleteFriend(id, friendId);
    }

}