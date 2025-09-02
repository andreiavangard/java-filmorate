package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public interface FilmService {

    Film setLike(Long filmId, Long userId);

    Film deleteLike(Long filmId, Long userId);

    Collection<Film> findAll();

    Collection<Film> findPopular(int count);

    Film create(Film film);

    Film update(Film newFilm);

    Optional<Film> findById(Long id);

    Map<Long, Film> getMapFilms();

}
