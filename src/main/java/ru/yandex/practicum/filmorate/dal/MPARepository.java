package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

@Repository
public class MPARepository extends BaseRepository<MPA> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM mpa";
    private static final String FIND_QUERY = "SELECT * FROM mpa WHERE mpa_id = ?";

    public MPARepository(JdbcTemplate jdbc, RowMapper<MPA> mapper) {
        super(jdbc, mapper);
    }

    public List<MPA> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<MPA> findById(Integer genreId) {
        return findOne(FIND_QUERY, genreId);
    }

}
