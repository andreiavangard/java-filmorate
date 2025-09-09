package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.CreateUserDto;
import ru.yandex.practicum.filmorate.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static CreateUserDto createUserDtoFromUser(User user) {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setEmail(user.getEmail());
        createUserDto.setLogin(user.getLogin());
        createUserDto.setName(user.getName());
        createUserDto.setBirthday(user.getBirthday());
        return createUserDto;
    }

    public static User userFromCreateUserDto(CreateUserDto createUser) {
        User user = new User();
        user.setEmail(createUser.getEmail());
        user.setLogin(createUser.getLogin());
        user.setName(createUser.getName());
        user.setBirthday(createUser.getBirthday());
        return user;
    }


}
