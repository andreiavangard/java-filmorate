package ru.yandex.practicum.filmorate.storage.MPA;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;
import java.util.Optional;

public interface MPAStorage {
    Collection<MPA> findAll();

    Optional<MPA> findById(Integer id);
}
