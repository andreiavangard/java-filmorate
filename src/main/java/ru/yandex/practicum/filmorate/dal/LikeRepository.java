package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikeRepository extends BaseRepository<Long> {
    private static final RowMapper<Long> longMapper = (resultSet, rowNum) -> resultSet.getLong("user_id");

    private static final String SET_QUERY = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String GET_FILM_LIKES_QUERY = "SELECT user_id FROM likes WHERE film_id = ?";


    public LikeRepository(JdbcTemplate jdbc) {
        super(jdbc, longMapper);
    }

    public void setLike(long filmId, long userId) {
        insertWithoutId(SET_QUERY, filmId, userId);
    }

    public boolean deleteLike(long filmId, long userId) {
        return delete(DELETE_QUERY, filmId, userId);
    }

    public List<Long> getFilmLikes(long filmId) {
        return findMany(GET_FILM_LIKES_QUERY, filmId);
    }

}
