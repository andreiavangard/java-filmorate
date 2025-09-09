package ru.yandex.practicum.filmorate.service.Like;

public interface LikeService {
    void setLike(long film_id, long user_id);

    boolean deleteLike(long film_id, long user_id);
}
