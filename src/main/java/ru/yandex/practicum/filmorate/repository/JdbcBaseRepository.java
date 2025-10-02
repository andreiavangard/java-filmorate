package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcBaseRepository<T> {
    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<T> mapper;

    protected Optional<T> findOne(String sql, MapSqlParameterSource params) {
        try {
            T result = jdbc.queryForObject(sql, params, mapper);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    protected <T> List<T> findManyExtract(String sql, MapSqlParameterSource params,
                                          ResultSetExtractor<List<T>> extractor) {
        return jdbc.query(sql, params, extractor);
    }

    public void batchInsertFilmGenres(String sql, Long filmId, List<Genre> genres) {
        //String sql = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        SqlParameterSource[] batchParams = genres.stream()
                .map(
                        genre -> {
                            MapSqlParameterSource params = new MapSqlParameterSource();
                            params.addValue("filmId", filmId);
                            params.addValue("genreId", genre.getId());
                            return params;
                        }
                )
                .toArray(SqlParameterSource[]::new);
        jdbc.batchUpdate(sql, batchParams);

    }

    public void batchInsertLikemGenres(String sql, Long filmId, List<Long> likes) {
        SqlParameterSource[] batchParams = likes.stream()
                .map(
                        like -> {
                            MapSqlParameterSource params = new MapSqlParameterSource();
                            params.addValue("filmId", filmId);
                            params.addValue("userId", like);
                            return params;
                        }
                )
                .toArray(SqlParameterSource[]::new);
        jdbc.batchUpdate(sql, batchParams);
    }

    protected List<T> findMany(String sql, MapSqlParameterSource params) {
        return jdbc.query(sql, params, mapper);
    }

    protected long insert(String sql, MapSqlParameterSource params, String nameKey) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder, new String[]{nameKey});
        Long id = keyHolder.getKeyAs(Long.class);

        if (id != null) {
            return id;
        } else {
            throw new InternalServerException("Не удалось сохранить данные");
        }
    }

    protected void insertWithoutId(String sql, MapSqlParameterSource params) {
        jdbc.update(sql, params);
    }

    protected boolean update(String sql, MapSqlParameterSource params) {
        int i = jdbc.update(sql, params);
        return i > 0;
    }

    protected boolean delete(String sql, MapSqlParameterSource params) {
        int i = jdbc.update(sql, params);
        return i > 0;
    }
}
