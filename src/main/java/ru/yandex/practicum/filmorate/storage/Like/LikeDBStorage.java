package ru.yandex.practicum.filmorate.storage.Like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.LikeRepository;


import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeDBStorage implements LikeStorage {
    private final LikeRepository likeRepository;

    public void setLike(long filmId, long userId) {
        likeRepository.setLike(filmId, userId);
    }

    public boolean deleteLike(long filmId, long userId) {
        return likeRepository.deleteLike(filmId, userId);
    }

    public List<Long> getFilmLikes(long filmId) {
        return likeRepository.getFilmLikes(filmId);
    }

}
