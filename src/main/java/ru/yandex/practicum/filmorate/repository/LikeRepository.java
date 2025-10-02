package ru.yandex.practicum.filmorate.repository;

import java.util.List;
import java.util.Set;

public interface LikeRepository {

    void setLike(long filmId, long userId);

    void addtLikes(long filmId, Set<Long> likes);

    boolean deleteLike(long filmId, long userId);

    List<Long> getFilmLikes(long filmId);

}
