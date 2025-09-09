package ru.yandex.practicum.filmorate.service.Like;

public interface LikeService {
    void setLike(long filmId, long userId);

    boolean deleteLike(long filmId, long userId);
}
