package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.controller.FilmController;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class FilmorateApplicationTests {

    @Test
        //успешная валидация
    void validationFilmWasSuccessful() {
        Film film = Film.builder()
                .name("Фильм")
                .description("описание фильма")
                .releaseDate(LocalDate.of(2000, 12, 28))
                .duration(120)
                .build();
        boolean success = FilmController.validate(film);
        assertTrue("Валидация правильного фильма не прошла", success);
    }

    @Test
        //название фильма не может быть пустым
    void nameFilmCannotBeEmpty() {
        Film film = Film.builder()
                .name("")
                .description("описание фильма")
                .releaseDate(LocalDate.of(2000, 12, 28))
                .duration(120)
                .build();
        assertThrows(ValidationException.class, () -> {
            FilmController.validate(film);
        }, "Название фильма не заполнено!");
    }

    @Test
        //максимальная длина описания — 200 символов;
    void maximumDescriptionFilmLength200() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 205; i++) {
            sb.append('f');
        }
        ;
        String description = sb.toString();
        Film film = Film.builder()
                .name("Фильм")
                .description(description)
                .releaseDate(LocalDate.of(2000, 12, 28))
                .duration(120)
                .build();
        assertThrows(ValidationException.class, () -> {
            FilmController.validate(film);
        }, "Описание фильма не должно быть больше 200 символов");
    }

    @Test
        //дата релиза — не раньше 28 декабря 1895 года;
    void releaseDateFilmNotEarlierThanStartDate() {
        Film film = Film.builder()
                .name("Фильм")
                .description("Описание фильма")
                .releaseDate(LocalDate.of(1984, 12, 28))
                .duration(120)
                .build();
        assertThrows(ValidationException.class, () -> {
            FilmController.validate(film);
        }, "Дата релиза должна быть больше 28 декабря 1895");
    }

    @Test
        //Продолжительность фильма должна быть положительной;
    void engthOfTheFilmMustBePositive() {
        Film film = Film.builder()
                .name("Фильм")
                .description("Описание фильма")
                .releaseDate(LocalDate.of(1984, 12, 28))
                .duration(0)
                .build();
        assertThrows(ValidationException.class, () -> {
            FilmController.validate(film);
        }, "Продолжительность фильма должна быть положительной");
    }

    @Test
        //успешная валидация пользователя
    void validationUserWasSuccessful() {
        User user = User.builder()
                .name("Иванов иван")
                .login("Ivanov_I")
                .email("Ivanov@m.ru")
                .birthday(LocalDate.of(1985, 7, 20))
                .build();
        boolean success = UserController.validate(user);
        assertTrue("Валидация правильного фильма не прошла", success);
    }
    //электронная почта не может быть пустой и должна содержать символ @;
    //логин не может быть пустым и содержать пробелы;
    //имя для отображения может быть пустым — в таком случае будет использован логин;
    //дата рождения не может быть в будущем.


    @Test
        //электронная почта не может быть пустой
    void emailMustBeProvided() {
        User user = User.builder()
                .name("Иванов иван")
                .login("Ivanov_I")
                .email("")
                .birthday(LocalDate.of(1985, 7, 20))
                .build();

        assertThrows(ValidationException.class, () -> {
            UserController.validate(user);
        }, "Электронный адрес не может быть пустым");
    }

    @Test
        //Неправильный формат электронного адреса
    void incorrectEmailAddressFormat() {
        User user = User.builder()
                .name("Иванов иван")
                .login("Ivanov_I")
                .email("Ivanov_I.ru")
                .birthday(LocalDate.of(1985, 7, 20))
                .build();

        assertThrows(ValidationException.class, () -> {
            UserController.validate(user);
        }, "Неправильный формат электронного адреса");
    }

    @Test
        //Логин не может быть пустым
    void loginCannotBeEmpty() {
        User user = User.builder()
                .name("Иванов иван")
                .login("")
                .email("Ivanov_I@m.ru")
                .birthday(LocalDate.of(1985, 7, 20))
                .build();

        assertThrows(ValidationException.class, () -> {
            UserController.validate(user);
        }, "Логин не может быть пустым");
    }

    @Test
        //Логин не может содержать пробелы
    void loginCannotContainSpaces() {
        User user = User.builder()
                .name("Иванов иван")
                .login("Ivanov I")
                .email("Ivanov_I@m.ru")
                .birthday(LocalDate.of(1985, 7, 20))
                .build();

        assertThrows(ValidationException.class, () -> {
            UserController.validate(user);
        }, "Логин не может содержать пробелы");
    }

    @Test
        //Дата рождения не может быть в будущем
    void dateOfBirthCannotBeInTheFuture() {
        LocalDate futureDate = LocalDate.now().plusMonths(1);

        User user = User.builder()
                .name("Иванов иван")
                .login("Ivanov I")
                .email("Ivanov_I@m.ru")
                .birthday(futureDate)
                .build();
        assertThrows(ValidationException.class, () -> {
            UserController.validate(user);
        }, "Дата рождения не может быть в будущем");
    }


}
