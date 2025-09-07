package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface UserStorage {
    public Collection<User> findAll();

    public Map<Long, User> getMapUsers();

    User create(User user);

    public User update(User newUser);

    Optional<User> findById(Long id);

    User addFriend(Long id, Long friendId);

    Collection<User> findFriends(Long userId);

    User deleteFriend(Long userId, Long friendId);

}
