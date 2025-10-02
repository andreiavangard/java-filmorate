package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcLikeRepository.class})
@RequiredArgsConstructor(onConstructor_=@Autowired)
@DisplayName("LikeRepository")
public class LikeRepositoryTest {
    private final JdbcLikeRepository jdbcLikeRepository;

    @Test
    @DisplayName("Тест должен установить лайк")
    public void test_should_set_like(){
        jdbcLikeRepository.setLike(3L, 1L);
        List<Long> likes = jdbcLikeRepository.getFilmLikes(1);
        assertThat(likes)
                .isNotNull()
                .hasSize(1)
                .containsOnly(1L);

    }

    @Test
    @DisplayName("Тест должен установить лайки фильму")
    public void test_should_set_likes(){
        Set<Long> likes = new HashSet<>();
        likes.add(1L);
        likes.add(2L);

        jdbcLikeRepository.addtLikes(4L, likes);

        List<Long> likesRepo = jdbcLikeRepository.getFilmLikes(4L);

        assertThat(likesRepo)
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    @DisplayName("Тест должен удалять лайки фильму")
    public void test_should_delete_likes(){
        boolean success = jdbcLikeRepository.deleteLike(3L, 2L);
        assertThat(success).isTrue();
        List<Long> likesRepo = jdbcLikeRepository.getFilmLikes(3L);
        assertThat(likesRepo)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Тест должен получить лайки для фильма фильму")
    public void  test_should_get_likes_for_film(){
        List<Long> likes = jdbcLikeRepository.getFilmLikes(2L);
        assertThat(likes)
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(1L, 2L);
    }
}
