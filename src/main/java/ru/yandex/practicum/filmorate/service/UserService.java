package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;


public interface UserService {

    User addFriend(Long userId, Long friendId);

    Collection<User> findFriends(Long userId);

    User deleteFriend(Long userId, Long friendId);

    Collection<User> findCommonFriends(Long userId, Long otherId);

    Collection<User> findAll();

    Optional<User> findById(Long id);

    User create(User user);

    User update(User newUser);

    Map<Long, User> getMapUsers();

}
