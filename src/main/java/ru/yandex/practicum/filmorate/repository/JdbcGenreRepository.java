package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcGenreRepository extends JdbcBaseRepository<Genre> implements GenreRepository {
    private static final String FIND_ALL_QUERY = "SELECT * FROM genres";
    private static final String FIND_QUERY = "SELECT * FROM genres WHERE genre_id = :genreId";
    private static final String INSERT_GENRE_FILM = "INSERT INTO film_genre (film_id, genre_id) VALUES (:film_id, :genreId)";
    private static final String FIND_FILM_GENRES_QUERY =
            "SELECT * FROM genres AS g "
                    + "INNER JOIN genresfilms AS gf ON g.genre_id = gf.genre_id "
                    + "WHERE film_id = :filmId";

    public JdbcGenreRepository(NamedParameterJdbcOperations jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public List<Genre> findAll() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return findMany(FIND_ALL_QUERY, params);
    }

    public Optional<Genre> findById(Integer genreId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genreId", genreId);
        return findOne(FIND_QUERY, params);
    }

    public List<Genre> findByIdFilm(Long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("filmId", filmId);
        return findMany(FIND_FILM_GENRES_QUERY, params);
    }


}
