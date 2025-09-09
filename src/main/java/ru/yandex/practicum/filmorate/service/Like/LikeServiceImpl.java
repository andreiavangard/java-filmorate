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

    public void setLike(long film_id, long user_id) {
        checkFilling(film_id, user_id);
        likeStorage.setLike(film_id, user_id);
    }

    ;

    public boolean deleteLike(long film_id, long user_id) {
        checkFilling(film_id, user_id);
        return likeStorage.deleteLike(film_id, user_id);
    }

    ;

    private void checkFilling(long film_id, long user_id) {
        filmService.findById(film_id)
                .orElseThrow(() -> new NotFoundException("Фильм c id " + film_id + " не найден"));

        userService.findById(user_id)
                .orElseThrow(() -> new NotFoundException("Пользователя c id " + user_id + " не найдено"));
    }

}
