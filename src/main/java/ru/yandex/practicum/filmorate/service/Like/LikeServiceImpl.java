package ru.yandex.practicum.filmorate.service.Like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.LikeRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public void setLike(long filmId, long userId) {
        checkFilling(filmId, userId);
        likeRepository.setLike(filmId, userId);
    }

    public boolean deleteLike(long filmId, long userId) {
        checkFilling(filmId, userId);
        return likeRepository.deleteLike(filmId, userId);
    }

    private void checkFilling(long filmId, long userId) {
        filmRepository.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм c id " + filmId + " не найден"));

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя c id " + userId + " не найдено"));
    }

}
