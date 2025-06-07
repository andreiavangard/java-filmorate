package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Получили список пользователей");
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        log.debug("Начало добавления фильма {}", film);
        if (validate(film)) {
            film.setId(Service.getNextId(films));
            films.put(film.getId(), film);
        }
        log.debug("Окончание добавления фильма {}", film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        if (films.containsKey(newFilm.getId())) {
            log.debug("Начало обновления фильма {}", newFilm);
            Film oldFilm = films.get(newFilm.getId());
            if (validate(newFilm)) {
                oldFilm.setName(newFilm.getName());
                oldFilm.setDescription(newFilm.getDescription());
                oldFilm.setDuration(newFilm.getDuration());
                oldFilm.setReleaseDate(newFilm.getReleaseDate());
            }
            log.debug("Окончание обновления фильма {}", oldFilm);
            return oldFilm;
        }
        log.error("Фильм с id = {} не найден", newFilm.getId());
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    public static boolean validate(Film film) {
        if (film.getName().isBlank()) {
            log.error("Электронный адрес не может быть пустым, фильм {}", film);
            throw new ValidationException("Название фильма не заполнено!");
        }
        if (film.getDescription().length() > 200) {
            log.error("Описание фильма не должно быть больше 200 символов {}", film);
            throw new ValidationException("Описание фильма не должно быть больше 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза должна быть больше 28 декабря 1895 {}", film);
            throw new ValidationException("Дата релиза должна быть больше 28 декабря 1895");
        }
        if (film.getDuration() <= 0) {
            log.error("Продолжительность фильма должна быть положительной {}", film);
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }

        return true;
    }

}