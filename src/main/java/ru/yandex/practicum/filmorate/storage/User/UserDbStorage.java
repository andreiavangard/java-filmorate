package ru.yandex.practicum.filmorate.storage.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.FriendsRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dto.CreateUserDto;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("UserDBStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        CreateUserDto createUserDto = UserMapper.createUserDtoFromUser(user);
        Long userId = userRepository.create(createUserDto);
        User newUser = UserMapper.userFromCreateUserDto(createUserDto);
        newUser.setId(userId);
        return newUser;
    }

    public User update(User user) {
        if (userRepository.update(user)) {
            return user;
        } else {
            throw new InternalServerException("Не удалось обновить пользователя");
        }
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean addFriend(Long userId, Long friendId) {
        return friendsRepository.addFriend(userId, friendId);
    }

    public boolean deleteFriend(Long userId, Long friendId) {
        return friendsRepository.deleteFriend(userId, friendId);
    }

    public Collection<User> findFriends(Long userId) {
        return friendsRepository.findFriends(userId)
                .stream()
                .map(Friends::getFriendId)
                .map(userRepository::findById)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


}
