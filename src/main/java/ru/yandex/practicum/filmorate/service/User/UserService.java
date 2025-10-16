package ru.yandex.practicum.filmorate.service.User;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;


public interface UserService {

    boolean addFriend(Long userId, Long friendId);

    Collection<User> findFriends(Long userId);

    boolean deleteFriend(Long userId, Long friendId);

    Collection<User> findCommonFriends(Long userId, Long otherId);

    Collection<User> findAll();

    Optional<User> findById(Long id);

    User create(User user);

    User update(User newUser);

}
