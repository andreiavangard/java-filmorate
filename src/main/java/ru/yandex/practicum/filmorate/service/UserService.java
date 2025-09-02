package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

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
