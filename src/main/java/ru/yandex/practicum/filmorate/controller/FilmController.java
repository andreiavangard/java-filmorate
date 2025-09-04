package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Получили список пользователей");
        return filmService.findAll().values();
    }

    @GetMapping("/{id}")
    public Optional<Film> findById(@PathVariable Long id) {
        return filmService.findById(id);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopular(
            @RequestParam(defaultValue = "10") int count
    ) {
        return filmService.findPopular(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Начало добавления фильма {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        if (filmService.findAll().containsKey(newFilm.getId())) {
            log.debug("Начало обновления фильма {}", newFilm);
            return filmService.update(newFilm);
        }
        log.error("Фильм с id = {} не найден", newFilm.getId());
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    @PutMapping("{id}/like/{userId}")
    public Film setLike(
            @PathVariable Long id,
            @PathVariable Long userId
    ) {
        return filmService.setLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public Film deleteLike(
            @PathVariable Long id,
            @PathVariable Long userId
    ) {
        return filmService.deleteLike(id, userId);
    }

}