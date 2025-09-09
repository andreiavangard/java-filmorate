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
        Long user_id = userRepository.create(createUserDto);
        User newUser = UserMapper.userFromCreateUserDto(createUserDto);
        newUser.setId(user_id);
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

    public boolean addFriend(Long user_id, Long friend_id) {
        return friendsRepository.addFriend(user_id, friend_id);
    }

    public boolean deleteFriend(Long user_id, Long friend_id) {
        return friendsRepository.deleteFriend(user_id, friend_id);
    }

    public Collection<User> findFriends(Long user_id) {
        return friendsRepository.findFriends(user_id)
                .stream()
                .map(Friends::getFriendId)
                .map(userRepository::findById)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


}
