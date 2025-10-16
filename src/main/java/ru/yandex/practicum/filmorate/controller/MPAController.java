package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPA.MPAService;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MPAController {
    private final MPAService mpaService;

    @GetMapping
    public Collection<MPA> findAll() {
        log.debug("GET /mpa");
        return mpaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<MPA> get(@PathVariable int id) {
        log.debug("GET /mpa/{}", id);
        return mpaService.findById(id);
    }
}
