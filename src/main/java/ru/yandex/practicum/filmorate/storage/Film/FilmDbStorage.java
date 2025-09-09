package ru.yandex.practicum.filmorate.storage.Film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.Like.LikeStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component("FilmDbStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final FilmRepository filmRepository;
    private final LikeStorage likeStorage;
    private final GenreRepository genreRepository;


    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }

    public Film create(Film film) {
        Film filmCreate = filmRepository.create(film);
        updateGenreLikeFilm(film);
        return filmCreate;
    }

    public Film update(Film newFilm) {
        if (filmRepository.update(newFilm)) {
            updateGenreLikeFilm(newFilm);
            return newFilm;
        } else {
            throw new InternalServerException("Не удалось обновить фильм");
        }
    }

    public Optional<Film> findById(Long film_id) {
        Optional<Film> film = filmRepository.findById(film_id);
        if (!film.isEmpty()) {
            setLikeFilm(film.get());
            setGenreFilm(film.get());
        }
        return film;
    }

    public Collection<Film> findPopular(int count) {
        Collection<Film> films = filmRepository.getPopular(count);
        for (Film film : films) {
            setLikeFilm(film);
            setGenreFilm(film);
        }
        return films;
    }

    public void setLike(Long filmId, Long userId) {
        likeStorage.setLike(filmId, userId);
    }

    public boolean deleteLike(Long filmId, Long userId) {
        return likeStorage.deleteLike(filmId, userId);
    }

    private void setLikeFilm(Film film) {
        Long filmId = film.getId();
        List<Long> likes = likeStorage.getFilmLikes(filmId);
        Set<Long> likesSet = new HashSet<>(likes);
        film.setLikes(likesSet);
    }

    private void setGenreFilm(Film film) {
        Long filmId = film.getId();
        List<Genre> genresFilm = genreRepository.findByIdFilm(filmId);
        // Сортируем и сохраняем порядок для тестов
        Set<Genre> genresFilmSet = genresFilm.stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        film.setGenres(genresFilmSet);
    }

    private void updateGenreLikeFilm(Film film) {
        filmRepository.deleteGenre(film.getId());
        film.getGenres().stream()
                .map(Genre::getId)
                .forEach(genreId ->
                        filmRepository.addGenre(film.getId(), genreId)
                );

        film.getLikes().stream()
                .forEach(userId ->
                        likeStorage.setLike(film.getId(), userId)
                );

    }

}
