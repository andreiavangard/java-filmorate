package ru.yandex.practicum.filmorate.service.MPA;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MPA.MPAStorage;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class MPAServiceIml implements MPAService {
    private final MPAStorage mpaStorage;

    @Autowired
    public MPAServiceIml(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Collection<MPA> findAll() {
        return mpaStorage.findAll();
    }

    public Optional<MPA> findById(Integer genreId) {
        Optional mpa = mpaStorage.findById(genreId);
        if (mpa.isEmpty()) {
            throw new NotFoundException("Рейтинга с id=" + genreId + " не найдено");
        }
        return mpa;
    }
}
