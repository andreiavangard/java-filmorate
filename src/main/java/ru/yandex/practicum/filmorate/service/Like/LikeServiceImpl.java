package ru.yandex.practicum.filmorate.service.Like;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.service.Film.FilmService;
import ru.yandex.practicum.filmorate.service.User.UserService;
import ru.yandex.practicum.filmorate.storage.Like.LikeStorage;

@Slf4j
@Service
public class LikeServiceImpl implements LikeService {
    private final LikeStorage likeStorage;
    private final FilmService filmService;
    private final UserService userService;

    @Autowired
    public LikeServiceImpl(LikeStorage likeStorage,
                           FilmService filmService,
                           UserService userService) {
        this.likeStorage = likeStorage;
        this.filmService = filmService;
        this.userService = userService;
    }

    public void setLike(long filmId, long userId) {
        checkFilling(filmId, userId);
        likeStorage.setLike(filmId, userId);
    }

    ;

    public boolean deleteLike(long filmId, long userId) {
        checkFilling(filmId, userId);
        return likeStorage.deleteLike(filmId, userId);
    }

    ;

    private void checkFilling(long filmId, long userId) {
        filmService.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм c id " + filmId + " не найден"));

        userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя c id " + userId + " не найдено"));
    }

}
