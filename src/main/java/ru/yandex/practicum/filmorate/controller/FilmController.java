package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Film> findById(@PathVariable Long id) {
        return filmStorage.findById(id);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopular(
            @RequestParam(defaultValue = "10") int count
    ) {
        return filmStorage.findPopular(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        return filmStorage.update(newFilm);
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