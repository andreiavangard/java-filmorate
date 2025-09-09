package ru.yandex.practicum.filmorate.storage.Like;

import java.util.List;

public interface LikeStorage {
    void setLike(long filmId, long userId);

    boolean deleteLike(long filmId, long userId);

    List<Long> getFilmLikes(long filmId);
}
