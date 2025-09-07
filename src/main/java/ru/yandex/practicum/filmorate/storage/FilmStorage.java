package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface FilmStorage {

    Collection<Film> findAll();

    Map<Long, Film> getMapFilms();

    Film create(Film film);

    Film update(Film newFilm);

    Optional<Film> findById(Long id);

    Collection<Film> findPopular(int count);

    Film setLike(Long filmId, Long userId);

    Film deleteLike(Long filmId, Long userId);

}
