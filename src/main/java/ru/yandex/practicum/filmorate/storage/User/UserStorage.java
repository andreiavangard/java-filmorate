package ru.yandex.practicum.filmorate.storage.User;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    public Collection<User> findAll();

    User create(User user);

    public User update(User newUser);

    Optional<User> findById(Long id);

    boolean addFriend(Long id, Long friendId);

    Collection<User> findFriends(Long userId);

    boolean deleteFriend(Long userId, Long friendId);

}
