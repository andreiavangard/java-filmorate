package ru.yandex.practicum.filmorate.service.Film;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.Genre.GenreService;
import ru.yandex.practicum.filmorate.service.MPA.MPAService;
import ru.yandex.practicum.filmorate.storage.Film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.User.UserStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreService genreService;
    private final MPAService mpaService;

    @Autowired
    public FilmServiceImpl(@Qualifier("FilmDbStorage") FilmStorage filmStorage,
                           @Qualifier("UserDBStorage") UserStorage userStorage,
                           GenreService genreService,
                           MPAService mpaService) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreService = genreService;
        this.mpaService = mpaService;
    }

    public void setLike(Long filmId, Long userId) {
        checkFilling(filmId, userId);
        filmStorage.setLike(filmId, userId);
    }

    public boolean deleteLike(Long filmId, Long userId) {
        checkFilling(filmId, userId);
        return filmStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Optional<Film> findById(Long id) {
        return filmStorage.findById(id);
    }

    public Collection<Film> findPopular(int count) {
        return filmStorage.findPopular(count);
    }

    public Film create(Film film) {
        Set<Integer> genreIds = getSetGenreId(film);
        checkGenres(genreIds);
        checkMPA(film.getMpa().getId());
        return filmStorage.create(film);
    }

    public Film update(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Не указан id фильма");
        }

        Optional<Film> film = filmStorage.findById(newFilm.getId());
        if (film.isEmpty()) {
            throw new NotFoundException("Фильм с id=" + newFilm.getId() + " не найден");
        }

        Set<Integer> genreIds = getSetGenreId(newFilm);
        checkGenres(genreIds);
        checkMPA(newFilm.getMpa().getId());

        return filmStorage.update(newFilm);
    }

    private void checkFilling(Long filmId, Long userId) {
        Optional film = filmStorage.findById(filmId);
        if (film.isEmpty()) {
            throw new NotFoundException(String.format("Фильм с id %s не найден", filmId));
        }

        Optional user = userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        }
    }

    private void checkMPA(Integer mpaId) {
        mpaService.findById(mpaId)
                .orElseThrow(() -> new NotFoundException("Рейтинг с id=" + mpaId + " не существует"));
    }

    private void checkGenres(Set<Integer> genreIds) {
        for (Integer genreId : genreIds) {
            if (genreService.findById(genreId).isEmpty()) {
                throw new NotFoundException("Жанра с id=" + genreId + " не существует");
            }
        }
    }

    private Set<Integer> getSetGenreId(Film film) {
        return film.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
    }

}
