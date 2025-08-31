package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;

    }

    @GetMapping
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
        return userStorage.findById(id);
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
        return userStorage.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        return userStorage.update(newUser);
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