package ru.yandex.practicum.filmorate.storage.MPA;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.MPARepository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MPADbStorage implements MPAStorage {
    private final MPARepository mpaRepository;

    public Collection<MPA> findAll() {
        return mpaRepository.findAll();
    }

    public Optional<MPA> findById(Integer id) {
        return mpaRepository.findById(id);
    }
}
