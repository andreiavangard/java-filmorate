package ru.yandex.practicum.filmorate.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friends;

import java.util.Collection;

@Repository
public class JdbcFriendsRepository extends JdbcBaseRepository<Friends> implements FriendsRepository {
    private static final String ADD_QUERY = "INSERT INTO friends (user_id, friend_id) VALUES (:userId, :friendId)";
    private static final String DELETE_QUERY = "DELETE FROM friends WHERE user_id = :userId AND friend_id = :friendId";
    private static final String GET_QUERY = "SELECT friend_id, user_id, assent FROM friends WHERE user_id = :userId";

    public JdbcFriendsRepository(NamedParameterJdbcOperations jdbc, RowMapper<Friends> mapper) {
        super(jdbc, mapper);
    }

    public boolean addFriend(Long userId, Long friendId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("friendId", friendId);

        return update(ADD_QUERY, params);
    }

    public boolean deleteFriend(long userId, long friendId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("friendId", friendId);
        return delete(DELETE_QUERY, params);
    }

    public Collection<Friends> findFriends(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        return findMany(GET_QUERY, params);
    }

}
