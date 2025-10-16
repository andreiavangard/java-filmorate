package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMPARepository extends JdbcBaseRepository<MPA> implements MPARepository {
    private static final String FIND_ALL_QUERY = "SELECT * FROM mpa";
    private static final String FIND_QUERY = "SELECT * FROM mpa WHERE mpa_id = :genreId";

    public JdbcMPARepository(NamedParameterJdbcOperations jdbc, RowMapper<MPA> mapper) {
        super(jdbc, mapper);
    }

    public List<MPA> findAll() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return findMany(FIND_ALL_QUERY, params);
    }

    public Optional<MPA> findById(Integer genreId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genreId", genreId);
        return findOne(FIND_QUERY, params);
    }

}
