package ru.yandex.practicum.filmorate.service.MPA;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.repository.MPARepository;


import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MPAServiceIml implements MPAService {
    private final MPARepository mpaRepository;

    public Collection<MPA> findAll() {
        return mpaRepository.findAll();
    }

    public Optional<MPA> findById(Integer genreId) {
        Optional mpa = mpaRepository.findById(genreId);
        if (mpa.isEmpty()) {
            throw new NotFoundException("Рейтинга с id=" + genreId + " не найдено");
        }
        return mpa;
    }
}
