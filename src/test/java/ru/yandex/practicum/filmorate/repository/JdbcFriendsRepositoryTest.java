package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Friends;
import ru.yandex.practicum.filmorate.repository.mappers.FriendsRowMapper;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcFriendsRepository.class, FriendsRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("JdbcFriendsRepository")
public class JdbcFriendsRepositoryTest {
    public static final long TEST_USER_ID_1 = 1L;
    public static final long TEST_USER_ID_2 = 2L;
    public static final long TEST_USER_ID_3 = 3L;
    private final JdbcFriendsRepository jdbcFriendsRepository;

    @Test
    @DisplayName("Тест должен создать друга")
    public void test_should_create_user() {
        boolean success = jdbcFriendsRepository.addFriend(TEST_USER_ID_1, TEST_USER_ID_2);
        assertThat(success).isTrue();
        Collection<Friends> friends = jdbcFriendsRepository.findFriends(TEST_USER_ID_1);
        assertThat(friends)
                .isNotNull()
                .isNotEmpty()
                .extracting(Friends::getFriendId)
                .contains(2L);
    }

    @Test
    @DisplayName("Тест должен удалить друга")
    public void test_should_delete_user() {
        boolean success = jdbcFriendsRepository.deleteFriend(TEST_USER_ID_2, TEST_USER_ID_1);
        assertThat(success).isTrue();
        Collection<Friends> friends = jdbcFriendsRepository.findFriends(TEST_USER_ID_1);
        assertThat(friends).isEmpty();
    }

    @Test
    @DisplayName("Тест должен вывести список друзей")
    public void test_should_list_friends() {
        Collection<Friends> friends = jdbcFriendsRepository.findFriends(TEST_USER_ID_3);
        assertThat(friends)
                .isNotNull()
                .hasSize(2)
                .extracting(Friends::getFriendId)
                .containsExactlyInAnyOrder(1L, 2L);
    }
}
