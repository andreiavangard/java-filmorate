package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    public Map<Long, User> findAll() {
        //такое сложное решение потому как
        //1. Было получено замечание Лучше возвращать копию, чтобы защитить оригинальную коллекцию от модификации
        //то есть return users нельзя
        //2.при return Map.copyOf(films); не сохраняется сотрировка в users и падают тесты
        //по логике прикладного решения нам сотрировка не нужна и return Map.copyOf(users) должно работать корректно
        //но для тестов сохраняем сортрировку, замена new HashMap<>() на LinkedHashMap, treeMap не помогла
        return users.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
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
