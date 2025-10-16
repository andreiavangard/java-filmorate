package ru.yandex.practicum.filmorate.service.Film;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.*;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final MPARepository mpaRepository;
    private final LikeRepository likeRepository;

    public void setLike(Long filmId, Long userId) {
        checkFilling(filmId, userId);
        likeRepository.setLike(filmId, userId);
    }

    public boolean deleteLike(Long filmId, Long userId) {
        checkFilling(filmId, userId);
        return likeRepository.deleteLike(filmId, userId);
    }

    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }

    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }

    public Collection<Film> findPopular(int count) {
        return filmRepository.getPopular(count);
    }

    public Film create(Film film) {
        Set<Integer> genreIds = getSetGenreId(film);
        checkGenres(genreIds);
        checkMPA(film.getMpa().getId());
        return filmRepository.create(film);
    }

    public Film update(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Не указан id фильма");
        }

        Optional<Film> film = filmRepository.findById(newFilm.getId());
        if (film.isEmpty()) {
            throw new NotFoundException("Фильм с id=" + newFilm.getId() + " не найден");
        }

        Set<Integer> genreIds = getSetGenreId(newFilm);
        checkGenres(genreIds);
        checkMPA(newFilm.getMpa().getId());

        if (filmRepository.update(newFilm)) {
            updateGenreFilm(newFilm);
            return newFilm;
        } else {
            throw new InternalServerException("Не удалось обновить фильм");
        }
    }

    private void checkFilling(Long filmId, Long userId) {
        Optional<Film> film = filmRepository.findById(filmId);
        if (film.isEmpty()) {
            throw new NotFoundException(String.format("Фильм с id %s не найден", filmId));
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        }
    }

    private void checkMPA(Integer mpaId) {
        mpaRepository.findById(mpaId)
                .orElseThrow(() -> new NotFoundException("Рейтинг с id=" + mpaId + " не существует"));
    }

    private void checkGenres(Set<Integer> genres) {
        Set<Integer> generesInRepo = genreRepository.findAll().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
        if (!genres.isEmpty() && !generesInRepo.containsAll(genres)) {
            throw new NotFoundException("Переданы несуществующие жанры" + genres + "---" + generesInRepo);
        }
    }

    private Set<Integer> getSetGenreId(Film film) {
        return film.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
    }

    private void setLikeFilm(Film film) {
        Long filmId = film.getId();
        List<Long> likes = likeRepository.getFilmLikes(filmId);
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

    private void updateGenreFilm(Film film) {
        filmRepository.deleteGenre(film.getId());
        filmRepository.addGenres(film.getId(), film.getGenres());
    }

}
