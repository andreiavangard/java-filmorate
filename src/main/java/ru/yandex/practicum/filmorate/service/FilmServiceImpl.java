package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film setLike(Long filmId, Long userId) {
        checkFilling(filmId, userId);
        return filmStorage.setLike(filmId, userId);
    }

    public Film deleteLike(Long filmId, Long userId) {
        checkFilling(filmId, userId);
        return filmStorage.deleteLike(filmId, userId);
    }

    public Map<Long, Film> findAll() {
        return filmStorage.findAll();
    }

    public Optional<Film> findById(Long id) {
        return filmStorage.findById(id);
    }

    public Collection<Film> findPopular(int count) {
        return filmStorage.findPopular(count);
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film newFilm) {
        return filmStorage.update(newFilm);
    }

    private void checkFilling(Long filmId, Long userId) {
        Optional film = filmStorage.findById(filmId);
        if (film.isEmpty()) {
            throw new NotFoundException(String.format("Фильм с id %s не найден", filmId));
        }

        Optional user = userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        }
    }

}
