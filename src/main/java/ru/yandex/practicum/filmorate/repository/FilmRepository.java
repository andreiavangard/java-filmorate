package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface FilmRepository {

    Collection<Film> findAll();

    Optional<Film> findById(Long filmId);

    Film create(Film film);

    boolean update(Film film);

    boolean delete(Long filmId);

    Collection<Film> getPopular(int count);

    void addGenres(long filmId, Set<Genre> genres);

    boolean deleteGenre(Long filmId);

}
