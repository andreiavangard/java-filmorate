package ru.yandex.practicum.filmorate.service.MPA;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;
import java.util.Optional;


public interface MPAService {
    Collection<MPA> findAll();

    Optional<MPA> findById(Integer id);
}
