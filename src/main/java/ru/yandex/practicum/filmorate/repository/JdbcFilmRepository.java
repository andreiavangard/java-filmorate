package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.Extractors.FilmWithItemsExtractor;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcFilmRepository extends JdbcBaseRepository<Film> implements FilmRepository {
    private static final String FIND_ALL_QUERY =
            "SELECT "
                    + "f.film_id, "
                    + "f.name, "
                    + "f.description, "
                    + "f.releaseDate, "
                    + "f.duration, "
                    + "m.mpa_id, "
                    + "m.name AS mpa_name, "
                    + "m.description AS mpa_description, "
                    + "g.genre_id AS genre_id, "
                    + "g.name AS genre_name, "
                    + "g.description AS genre_description "
                    + "FROM films AS f "
                    + "LEFT JOIN mpa AS m ON f.mpa_id = m.mpa_id "
                    + "LEFT JOIN genresfilms AS gf ON gf.film_id = f.film_id "
                    + "LEFT JOIN genres AS g ON g.genre_id = gf.genre_id "
                    + "ORDER BY f.film_id, g.genre_id  ";
    private static final String FIND_BY_ID_QUERY =
            "SELECT "
                    + "f.film_id, "
                    + "f.name, "
                    + "f.description, "
                    + "f.releaseDate, "
                    + "f.duration, "
                    + "m.mpa_id, "
                    + "m.name AS mpa_name, "
                    + "m.description AS mpa_description, "
                    + "g.genre_id AS genre_id, "
                    + "g.name AS genre_name, "
                    + "g.description AS genre_description "
                    + "FROM films AS f "
                    + "LEFT JOIN mpa AS m ON f.mpa_id = m.mpa_id "
                    + "LEFT JOIN genresfilms AS gf ON gf.film_id = f.film_id "
                    + "LEFT JOIN genres AS g ON g.genre_id = gf.genre_id "
                    + "WHERE f.film_id = :filmId "
                    + "ORDER BY f.film_id, g.genre_id  ";
    private static final String GET_POPULAR =
            "SELECT "
                    + "f.film_id, "
                    + "f.name, "
                    + "f.description, "
                    + "f.releaseDate, "
                    + "f.duration, "
                    + "m.mpa_id, "
                    + "m.name AS mpa_name, "
                    + "m.description AS mpa_description, "
                    + "g.genre_id AS genre_id, "
                    + "g.name AS genre_name, "
                    + "g.description AS genre_description, "
                    + "COUNT(l.user_id) as countLikes "
                    + "FROM films AS f "
                    + "LEFT JOIN mpa AS m ON f.mpa_id = m.mpa_id "
                    + "LEFT JOIN genresfilms AS gf ON gf.film_id = f.film_id "
                    + "LEFT JOIN genres AS g ON g.genre_id = gf.genre_id "
                    + "LEFT JOIN likes AS l ON f.film_id = l.film_id "
                    + "WHERE f.film_id in ("
                    + "SELECT "
                    + "film_id "
                    + "FROM "
                    + "(SELECT "
                    + "f.film_id, "
                    + "COUNT(l.user_id) as countLikes "
                    + "FROM films f "
                    + "LEFT JOIN likes AS l ON f.film_id = l.film_id "
                    + "GROUP BY f.film_id "
                    + "ORDER BY COUNT(l.user_id) DESC "
                    + "LIMIT :count) "
                    + ") "
                    + "GROUP BY f.film_id, f.name, f.description, f.releaseDate, f.duration, m.mpa_id, m.name,  m.description, g.genre_id, g.name, g.description "
                    + "ORDER BY COUNT(l.user_id) DESC, f.film_id, g.genre_id  ";

    private static final String INSERT_QUERY = "INSERT INTO films (name, description, releaseDate, duration, mpa_id)  VALUES (:name,:description,:releaseDate,:duration,:mpaId)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = :name, description = :description, releaseDate = :releaseDate, duration = :duration, mpa_id = :mpaId WHERE film_id = :filmId";
    private static final String DELETE_QUERY = "DELETE FROM films WHERE film_id = :filmId";

    private static final String ADD_GENERES_QUERY = "MERGE INTO genresfilms (film_id, genre_id) KEY (film_id, genre_id) VALUES (:filmId, :genreId)";
    private static final String DELETE_GENERES_QUERY = "DELETE FROM genresfilms WHERE film_id = :filmId";

    public JdbcFilmRepository(NamedParameterJdbcOperations jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public Collection<Film> findAll() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return findManyExtract(FIND_ALL_QUERY, params, new FilmWithItemsExtractor());
    }

    public Optional<Film> findById(Long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("filmId", filmId);
        List<Film> films = findManyExtract(FIND_BY_ID_QUERY, params, new FilmWithItemsExtractor());

        Optional<Film> filmOpt = films.stream().findAny();
        //костыль сортировки для тестов, в жизни он не нужен
        if (!filmOpt.isEmpty()) {
            Film film = filmOpt.get();
            Set<Genre> genresFilmSet = film.getGenres().stream()
                    .sorted(Comparator.comparing(Genre::getId))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            film.setGenres(genresFilmSet);
            return Optional.of(film);
        }

        return films.stream().findAny();
    }

    public Collection<Film> getPopular(int count) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("count", count);
        return findManyExtract(GET_POPULAR, params, new FilmWithItemsExtractor());
    }

    public Film create(Film film) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("releaseDate", Date.valueOf(film.getReleaseDate()));
        params.addValue("duration", film.getDuration());
        params.addValue("mpaId", film.getMpa().getId());
        Long filmId = insert(INSERT_QUERY, params, "film_id");
        film.setId(filmId);
        Set<Genre> genres = film.getGenres();
        if (!genres.isEmpty()) {
            batchInsertFilmGenres(ADD_GENERES_QUERY, filmId, genres.stream().toList());
        }
        return film;
    }

    public boolean update(Film film) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("releaseDate", Date.valueOf(film.getReleaseDate()));
        params.addValue("duration", film.getDuration());
        params.addValue("mpaId", film.getMpa().getId());
        params.addValue("filmId", film.getId());
        boolean filmUpdate = update(UPDATE_QUERY, params);
        if (!filmUpdate) {
            return filmUpdate;
        }
        Set<Genre> genres = film.getGenres();
        if (!genres.isEmpty()) {
            batchInsertFilmGenres(ADD_GENERES_QUERY, film.getId(), genres.stream().toList());
        }
        return true;
    }

    public boolean delete(Long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("filmId", filmId);
        return delete(DELETE_QUERY, params);
    }

    public void addGenres(long filmId, Set<Genre> genres) {
        if (!genres.isEmpty()) {
            batchInsertFilmGenres(ADD_GENERES_QUERY, filmId, genres.stream().toList());
        }
    }

    public boolean deleteGenre(Long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("filmId", filmId);
        return delete(DELETE_GENERES_QUERY, params);
    }

}
