package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Service;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private final Comparator<Film> filmLikesComparator = Comparator.comparing(Film::getCountLikes);

    public Collection<Film> findAll() {
        log.debug("Получили список пользователей");
        return films.values();
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
        log.debug("Начало добавления фильма {}", film);
        film.setId(Service.getNextId(films));
        films.put(film.getId(), film);
        log.debug("Окончание добавления фильма {}", film);
        return film;
    }

    public Film update(Film newFilm) {
        if (films.containsKey(newFilm.getId())) {
            log.debug("Начало обновления фильма {}", newFilm);
            Film oldFilm = films.get(newFilm.getId());
            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setDuration(newFilm.getDuration());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            log.debug("Окончание обновления фильма {}", oldFilm);
            return oldFilm;
        }
        log.error("Фильм с id = {} не найден", newFilm.getId());
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
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
