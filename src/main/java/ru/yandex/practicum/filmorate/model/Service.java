package ru.yandex.practicum.filmorate.model;

import java.util.Map;

public class Service {
    static public long getNextId(Map<Long, ?> collect) {
        long currentMaxId = collect.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
