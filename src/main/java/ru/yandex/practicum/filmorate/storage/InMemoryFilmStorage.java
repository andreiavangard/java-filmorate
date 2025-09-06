package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Service;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private final Comparator<Film> filmLikesComparator = Comparator.comparing(Film::getCountLikes);

    //возвращает коллекцию фильмов, служит для организации внешней логики приложения, содержит только фильмы
    public Collection<Film> findAll() {
        return films.values();
    }

    //возвращает map фильмов, служит для технических целей, когда нужна коллекция с id фильма и самим фильмом
    public Map<Long, Film> getMapFilms() {
        return Map.copyOf(films);
    }

    public Optional<Film> findById(Long id) {
        return Optional.ofNullable(films.get(id));
    }

    public Collection<Film> findPopular(int count) {
        return films.values()
                .stream()
                .sorted(filmLikesComparator.reversed())
                .limit(count)
                .toList();
    }

    public Film create(Film film) {
        film.setId(Service.getNextId(films));
        films.put(film.getId(), film);
        return film;
    }

    public Film update(Film newFilm) {
        Film oldFilm = films.get(newFilm.getId());
        oldFilm.setName(newFilm.getName());
        oldFilm.setDescription(newFilm.getDescription());
        oldFilm.setDuration(newFilm.getDuration());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        return oldFilm;
    }

    public Film setLike(Long filmId, Long userId) {
        Film film = films.get(filmId);
        film.setLike(userId);
        return film;
    }

    public Film deleteLike(Long filmId, Long userId) {
        Film film = films.get(filmId);
        film.deleteLike(userId);
        return film;
    }

}
