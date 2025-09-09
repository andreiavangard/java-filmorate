package ru.yandex.practicum.filmorate.storage.User;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component("InMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User create(User user) {
        user.setId(Service.getNextId(users));
        users.put(user.getId(), user);
        return user;
    }

    public User update(User newUser) {
        User oldUser = users.get(newUser.getId());
        oldUser.setName(newUser.getName());
        oldUser.setLogin(newUser.getLogin());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setBirthday(newUser.getBirthday());
        oldUser.setBirthday(newUser.getBirthday());

        return oldUser;
    }

    public boolean addFriend(Long userId, Long friendId) {
        User user = users.get(userId);
        User userFriend = users.get(friendId);

        user.setFriend(friendId);
        userFriend.setFriend(userId);

        return true;
    }

    public Collection<User> findFriends(Long userId) {
        User user = users.get(userId);
        Set<Long> usersIds = user.getIdsFriends();

        return usersIds.stream()
                .map(users::get)
                .toList();
    }

    public boolean deleteFriend(Long userId, Long friendId) {
        User user = users.get(userId);
        User userFriend = users.get(friendId);
        user.deleteFriend(friendId);
        userFriend.deleteFriend(userId);
        return true;
    }

}
