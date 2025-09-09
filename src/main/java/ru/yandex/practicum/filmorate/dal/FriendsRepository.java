package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friends;

import java.util.Collection;

@Repository
public class FriendsRepository extends BaseRepository<Friends> {
    private static final String ADD_QUERY = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
    private static final String GET_QUERY = "SELECT friend_id, user_id, assent FROM friends WHERE user_id = ?";

    public FriendsRepository(JdbcTemplate jdbc, RowMapper<Friends> mapper) {
        super(jdbc, mapper);
    }

    public boolean addFriend(Long userId, Long friendId) {
        return update(ADD_QUERY, userId, friendId);
    }

    public boolean deleteFriend(long userId, long friendId) {
        return delete(DELETE_QUERY, userId, friendId);
    }

    public Collection<Friends> findFriends(long userId) {
        return findMany(GET_QUERY, userId);
    }

}
