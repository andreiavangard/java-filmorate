package ru.yandex.practicum.filmorate.storage.Like;

import java.util.List;

public interface LikeStorage {
    void setLike(long film_id, long user_id);

    boolean deleteLike(long film_id, long user_id);

    List<Long> getFilmLikes(long filmId);
}
