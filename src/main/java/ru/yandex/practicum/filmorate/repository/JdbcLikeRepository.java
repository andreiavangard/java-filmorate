package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class JdbcLikeRepository extends JdbcBaseRepository<Long> implements LikeRepository {
    private static final RowMapper<Long> longMapper = (resultSet, rowNum) -> resultSet.getLong("user_id");

    private static final String SET_QUERY = "INSERT INTO likes (film_id, user_id) VALUES (:filmId, :userId)";
    private static final String DELETE_QUERY = "DELETE FROM likes WHERE film_id = :filmId AND user_id = :userId";
    private static final String GET_FILM_LIKES_QUERY = "SELECT user_id FROM likes WHERE film_id = :filmId";


    public JdbcLikeRepository(NamedParameterJdbcOperations jdbc) {
        super(jdbc, longMapper);
    }

    public void setLike(long filmId, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("filmId", filmId);
        params.addValue("userId", userId);
        insertWithoutId(SET_QUERY, params);
    }

    public void addtLikes(long filmId, Set<Long> likes) {
        if (!likes.isEmpty()) {
            batchInsertLikemGenres(SET_QUERY, filmId, likes.stream().toList());
        }
    }

    public boolean deleteLike(long filmId, long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("filmId", filmId);
        params.addValue("userId", userId);
        return delete(DELETE_QUERY, params);
    }

    public List<Long> getFilmLikes(long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("filmId", filmId);
        return findMany(GET_FILM_LIKES_QUERY, params);
    }

}
