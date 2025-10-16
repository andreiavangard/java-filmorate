package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friends {
    private Long id;
    private Long friendId;
    private boolean assent;
}
