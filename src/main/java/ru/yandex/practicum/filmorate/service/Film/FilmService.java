package ru.yandex.practicum.filmorate.service.Film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public interface FilmService {

    void setLike(Long filmId, Long userId);

    boolean deleteLike(Long filmId, Long userId);

    Collection<Film> findAll();

    Collection<Film> findPopular(int count);

    Film create(Film film);

    Film update(Film newFilm);

    Optional<Film> findById(Long id);

}
