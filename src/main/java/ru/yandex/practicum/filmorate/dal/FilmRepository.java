package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {
    private static final String FIND_ALL_QUERY =
            "SELECT"
                    + "f.film_id,"
                    + "f.name,"
                    + "f.description,"
                    + "f.releaseDate,"
                    + "f.duration,"
                    + "m.mpa_id, "
                    + "m.name AS mpa_name"
                    + "FROM films AS f "
                    + "LEFT JOIN mpa AS m ON f.mpa_id = m.mpa_id";
    private static final String FIND_BY_ID_QUERY =
            "SELECT"
                    + "f.film_id,"
                    + "f.name,"
                    + "f.description,"
                    + "f.releaseDate,"
                    + "f.duration,"
                    + "m.mpa_id,"
                    + "m.name AS mpa_name"
                    + "FROM films AS f"
                    + "LEFT JOIN mpa AS m ON f.mpa_id = m.mpa_id"
                    + "WHERE f.film_id = ?";
    private static final String GET_POPULAR =
            "SELECT"
                    + "f.film_id,"
                    + "f.name,"
                    + "f.description,"
                    + "f.releaseDate,"
                    + "f.duration,"
                    + "m.mpa_id,"
                    + "m.name AS mpa_name,"
                    + "COUNT(l.user_id) as countLikes"
                    + "FROM films f"
                    + "LEFT JOIN likes AS l ON f.film_id = l.film_id"
                    + "LEFT JOIN mpa AS m ON f.mpa_id = m.mpa_id"
                    + "GROUP BY f.film_id, f.name, f.description, f.releaseDate, f.duration, m.mpa_id, m.name"
                    + "ORDER BY COUNT(l.user_id) DESC"
                    + "LIMIT ?";
    private static final String INSERT_QUERY = "INSERT INTO films (name, description, releaseDate, duration, mpa_id)  VALUES (?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, releaseDate = ?, duration = ?, mpa_id = ? WHERE film_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM films WHERE film_id = ?";

    private static final String ADD_GENERES_QUERY = "MERGE INTO genresfilms (film_id, genre_id) KEY (film_id, genre_id) VALUES (?, ?)";
    private static final String DELETE_GENERES_QUERY = "DELETE FROM genresfilms WHERE film_id = ?";

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public Collection<Film> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<Film> findById(Long filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId);
    }

    public Film create(Film film) {
        Long film_id = insert(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId());
        film.setId(film_id);
        return film;
    }

    public boolean update(Film film) {
        return update(UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
    }

    public boolean delete(Long filmId) {
        return delete(DELETE_QUERY, filmId);
    }

    public Collection<Film> getPopular(int count) {
        return findMany(GET_POPULAR, count);
    }

    public boolean addGenre(long film_id, long genreId) {
        return update(ADD_GENERES_QUERY, film_id, genreId);
    }

    public boolean deleteGenre(Long filmId) {
        return delete(DELETE_GENERES_QUERY, filmId);
    }

}
