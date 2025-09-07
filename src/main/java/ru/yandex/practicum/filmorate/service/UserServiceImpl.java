package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(Long userId, Long friendId) {
        checkFilling(userId);
        checkFilling(friendId);
        return userStorage.addFriend(userId, friendId);
    }

    public Collection<User> findFriends(Long userId) {
        checkFilling(userId);
        return userStorage.findFriends(userId);
    }

    public User deleteFriend(Long userId, Long friendId) {
        checkFilling(userId);
        checkFilling(friendId);
        return userStorage.deleteFriend(userId, friendId);
    }

    private void checkFilling(Long userId) {
        Optional user = userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        }
    }

    public Collection<User> findCommonFriends(Long userId, Long otherId) {
        Collection<User> userCollect = userStorage.findFriends(userId);
        Collection<User> userOtherCollect = userStorage.findFriends(otherId);

        return userCollect.stream()
                .filter(user1 -> userOtherCollect.stream().anyMatch(user2 -> user2.getId().compareTo(user1.getId()) == 0))
                .toList();
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public Map<Long, User> getMapUsers() {
        return userStorage.getMapUsers();
    }

    public Optional<User> findById(Long id) {
        return userStorage.findById(id);
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User newUser) {
        return userStorage.update(newUser);
    }

}
