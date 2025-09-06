package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Service;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private final Comparator<Film> filmLikesComparator = Comparator.comparing(Film::getCountLikes);

    public Map<Long, Film> findAll() {
        //такое сложное решение потому как
        //1. Было получено замечание Лучше возвращать копию, чтобы защитить оригинальную коллекцию от модификации
        //то есть return films нельзя
        //2.при return Map.copyOf(films); не сохраняется сотрировка в films и падают тесты
        //по логике прикладного решения нам сотрировка не нужна и return Map.copyOf(films) должно работать корректно
        //но для тестов сохраняем сортрировку, замена new HashMap<>() на LinkedHashMap, treeMap не помогла
        return films.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
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
