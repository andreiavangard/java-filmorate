package ru.yandex.practicum.filmorate.service.Genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepository;


import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceIml implements GenreService {
    private final GenreRepository genreRepository;

    public Collection<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Optional<Genre> findById(Integer genreId) {
        Optional genre = genreRepository.findById(genreId);
        if (genre.isEmpty()) {
            throw new NotFoundException("Жанра с id=" + genreId + " не найдено");
        }
        return genre;
    }
}
