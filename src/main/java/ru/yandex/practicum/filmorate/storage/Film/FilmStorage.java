package ru.yandex.practicum.filmorate.storage.Film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    Collection<Film> findAll();

    Film create(Film film);

    Film update(Film newFilm);

    Optional<Film> findById(Long id);

    Collection<Film> findPopular(int count);

    void setLike(Long filmId, Long userId);

    boolean deleteLike(Long filmId, Long userId);

}
