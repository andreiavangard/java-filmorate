package ru.yandex.practicum.filmorate.storage.Genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

@Component()
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final GenreRepository genreRepository;

    public Collection<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Optional<Genre> findById(Integer id) {
        return genreRepository.findById(id);
    }
}
