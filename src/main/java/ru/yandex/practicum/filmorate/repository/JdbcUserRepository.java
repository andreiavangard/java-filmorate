package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public class JdbcUserRepository extends JdbcBaseRepository<User> implements UserRepository {
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String INSERT_QUERY = "INSERT INTO users (email, login, name, birthday)  VALUES (:email,:login,:name,:birthday)";
    private static final String UPDATE_QUERY = "UPDATE users SET email = :email, login = :login, name = :name, birthday = :birthday WHERE user_id = :userId";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = :userId";


    public JdbcUserRepository(NamedParameterJdbcOperations jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public Collection<User> findAll() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return findMany(FIND_ALL_QUERY, params);
    }

    public User create(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("login", user.getLogin());
        params.addValue("email", user.getEmail());
        params.addValue("birthday", user.getBirthday());

        Long userId = insert(INSERT_QUERY, params, "user_id");
        user.setId(userId);
        return user;
    }

    public boolean update(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("login", user.getLogin());
        params.addValue("email", user.getEmail());
        params.addValue("birthday", user.getBirthday());
        params.addValue("userId", user.getId());
        return update(UPDATE_QUERY, params);
    }

    public Optional<User> findById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", id);
        return findOne(FIND_BY_ID_QUERY, params);
    }
}
