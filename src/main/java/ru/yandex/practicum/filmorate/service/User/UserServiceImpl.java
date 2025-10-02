package ru.yandex.practicum.filmorate.service.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FriendsRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;

    public boolean addFriend(Long userId, Long friendId) {
        checkFilling(userId);
        checkFilling(friendId);
        return friendsRepository.addFriend(userId, friendId);
    }

    public Collection<User> findFriends(Long userId) {
        checkFilling(userId);
        return friendsRepository.findFriends(userId)
                .stream()
                .map(Friends::getFriendId)
                .map(userRepository::findById)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public boolean deleteFriend(Long userId, Long friendId) {
        checkFilling(userId);
        checkFilling(friendId);
        return friendsRepository.deleteFriend(userId, friendId);
    }

    private void checkFilling(Long userId) {
        Optional user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        }
    }

    public Collection<User> findCommonFriends(Long userId, Long otherId) {
        Collection<User> userCollect = friendsRepository.findFriends(userId)
                .stream()
                .map(Friends::getFriendId)
                .map(userRepository::findById)
                .map(Optional::get)
                .collect(Collectors.toList());

        Collection<User> userOtherCollect = friendsRepository.findFriends(otherId)
                .stream()
                .map(Friends::getFriendId)
                .map(userRepository::findById)
                .map(Optional::get)
                .collect(Collectors.toList());

        return userCollect.stream()
                .filter(user1 -> userOtherCollect.stream().anyMatch(user2 -> user2.getId().compareTo(user1.getId()) == 0))
                .toList();
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("Пользователя с id=" + id + " не найдено");
        }
        return user;
    }

    public User create(User user) {
        return userRepository.create(user);
    }

    public User update(User newUser) {
        if (userRepository.update(newUser)) {
            return newUser;
        } else {
            throw new InternalServerException("Не удалось обновить пользователя");
        }
    }

}
