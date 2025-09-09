package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository extends BaseRepository<Genre> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM genres";
    private static final String FIND_QUERY = "SELECT * FROM genres WHERE genre_id = ?";
    private static final String FIND_FILM_GENRES_QUERY =
                    "SELECT * FROM genres AS g"
                    +"INNER JOIN genresfilms AS gf ON g.genre_id = gf.genre_id"
                    +"WHERE film_id = ?";

    public GenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public List<Genre> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<Genre> findById(Integer genreId) {
        return findOne(FIND_QUERY, genreId);
    }

    public List<Genre> findByIdFilm(Long filmId) {
        return findMany(FIND_FILM_GENRES_QUERY, filmId);
    }

}
