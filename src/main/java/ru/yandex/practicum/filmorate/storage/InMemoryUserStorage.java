package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> findAll() {
        log.debug("Получили список пользователей");
        return users.values();
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User create(User user) {
        log.debug("Начало добавления пользователя {}", user);
        user.setId(Service.getNextId(users));
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.debug("Для пользователя в качестве имени указан логин");
        }
        users.put(user.getId(), user);
        log.debug("Добавлен пользователь {}", user);
        return user;
    }

    public User update(User newUser) {
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

    public User addFriend(Long userId, Long friendId) {
        User user = users.get(userId);
        User userFriend = users.get(friendId);

        user.setFriend(friendId);
        userFriend.setFriend(userId);

        return user;
    }

    public Collection<User> findFriends(Long userId) {
        User user = users.get(userId);
        Set<Long> usersIds = user.getIdsFriends();

        return usersIds.stream()
                .map(users::get)
                .toList();
    }

    public User deleteFriend(Long userId, Long friendId) {
        User user = users.get(userId);
        User userFriend = users.get(friendId);
        user.deleteFriend(friendId);
        userFriend.deleteFriend(userId);
        return user;
    }

}
