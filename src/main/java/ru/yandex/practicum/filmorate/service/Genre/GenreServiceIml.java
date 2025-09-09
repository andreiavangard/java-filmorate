package ru.yandex.practicum.filmorate.service.Genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.Genre.GenreStorage;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class GenreServiceIml implements GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreServiceIml(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Collection<Genre> findAll() {
        return genreStorage.findAll();
    }

    public Optional<Genre> findById(Integer genre_id) {
        Optional genre = genreStorage.findById(genre_id);
        if (genre.isEmpty()) {
            throw new NotFoundException("Жанра с id=" + genre_id + " не найдено");
        }
        return genre;
    }
}
