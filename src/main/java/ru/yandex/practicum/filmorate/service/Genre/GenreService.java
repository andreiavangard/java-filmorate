package ru.yandex.practicum.filmorate.service.Genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;


public interface GenreService {

    Collection<Genre> findAll();

    Optional<Genre> findById(Integer id);

}
