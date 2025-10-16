package ru.yandex.practicum.filmorate.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MPARowMapper implements RowMapper<MPA> {
    public MPA mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        MPA mpa = new MPA();
        mpa.setId(resultSet.getInt("mpa_id"));
        mpa.setName(resultSet.getString("name"));
        mpa.setDescription(resultSet.getString("description"));
        return mpa;
    }
}
