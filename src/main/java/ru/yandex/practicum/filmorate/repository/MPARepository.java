package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

public interface MPARepository {

    public List<MPA> findAll();

    Optional<MPA> findById(Integer mpaId);

}
