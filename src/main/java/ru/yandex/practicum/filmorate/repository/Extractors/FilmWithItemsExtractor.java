package ru.yandex.practicum.filmorate.repository.Extractors;

import org.springframework.jdbc.core.ResultSetExtractor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FilmWithItemsExtractor implements ResultSetExtractor<List<Film>> {
    @Override
    public List<Film> extractData(ResultSet rs) throws SQLException {
        List<Film> films = new ArrayList<>();
        Film currentFilm = null;
        Long currentFilmId = null;

        while (rs.next()) {
            Long filmId = rs.getLong("film_id");
            if (currentFilm == null || !filmId.equals(currentFilmId)) {
                if (currentFilm != null) {
                    films.add(currentFilm);
                }

                currentFilm = new Film();
                currentFilmId = filmId;
                currentFilm.setId(rs.getLong("film_id"));
                currentFilm.setName(rs.getString("name"));
                currentFilm.setDescription(rs.getString("description"));
                currentFilm.setDuration(rs.getInt("duration"));
                currentFilm.setReleaseDate(rs.getDate("releaseDate").toLocalDate());

                int mpaId = rs.getInt("mpa_id");
                if (!rs.wasNull()) {
                    MPA mpa = new MPA();
                    mpa.setId(mpaId);
                    mpa.setName(rs.getString("mpa_name"));
                    mpa.setDescription(rs.getString("mpa_description"));

                    currentFilm.setMpa(mpa);
                }
                currentFilm.setGenres(new HashSet<>());
            }
            int genreId = rs.getInt("genre_id");
            if (!rs.wasNull()) {
                Genre genre = new Genre();
                genre.setId(genreId);
                genre.setName(rs.getString("genre_name"));
                genre.setDescription(rs.getString("genre_description"));

                currentFilm.addGenre(genre);
            }

        }
        if (currentFilm != null) {
            films.add(currentFilm);
        }
        return films;
    }
}
