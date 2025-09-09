package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.User.UserService;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
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
    @ResponseStatus(HttpStatus.CREATED)
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
        log.debug("Начало обновления пользователя {}", newUser);
        User user = userService.findById(newUser.getId()).orElseThrow(
                () -> new NotFoundException("Указан id несуществующего пользователя"));
        return userService.update(newUser);
    }

    @PutMapping("{id}/friends/{friendId}")
    public void addFriend(
            @PathVariable Long id,
            @PathVariable Long friendId
    ) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriend(
            @PathVariable Long id,
            @PathVariable Long friendId
    ) {
        userService.deleteFriend(id, friendId);
    }

}