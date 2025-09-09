package ru.yandex.practicum.filmorate.storage.Like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.LikeRepository;


import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeDBStorage implements LikeStorage {
    private final LikeRepository likeRepository;

    public void setLike(long film_id, long user_id) {
        likeRepository.setLike(film_id, user_id);
    }

    ;

    public boolean deleteLike(long film_id, long user_id) {
        return likeRepository.deleteLike(film_id, user_id);
    }

    ;

    public List<Long> getFilmLikes(long filmId) {
        return likeRepository.getFilmLikes(filmId);
    }

}
