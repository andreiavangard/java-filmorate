package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.mappers.GenreRowMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@JdbcTest
@Import({JdbcGenreRepository.class, GenreRowMapper.class})
@RequiredArgsConstructor(onConstructor_=@Autowired)
@DisplayName("JdbcGenreRepository")
public class JdbcGenreRepositoryTest {
    private final JdbcGenreRepository jdbcGenreRepository;

    @Test
    @DisplayName("Тест должен получить жанр")
    public void test_must_get_genre(){
        Optional<Genre> genre = jdbcGenreRepository.findById(1);
        assertThat(genre)
                .isPresent()
                .get()
                .satisfies(g -> {
                    assertThat(g.getId()).isEqualTo(1);
                    assertThat(g.getName()).isEqualTo("Комедия");
                });

    }

    @Test
    @DisplayName("Тест должен получить все жанры")
    public void test_must_all_genres(){
        List<Genre> genres = jdbcGenreRepository.findAll();
        assertThat(genres)
                .isNotNull()
                .hasSize(6)
                .extracting(Genre::getId, Genre::getName)
                .containsExactlyInAnyOrder(
                        tuple(1, "Комедия"),
                        tuple(2, "Драма"),
                        tuple(3, "Мультфильм"),
                        tuple(4, "Триллер"),
                        tuple(5, "Документальный"),
                        tuple(6, "Боевик")
                );
    }

    @Test
    @DisplayName("Тест должен получить жанры по фильму")
    public void  test_should__genres_of_film(){
        List<Genre> genres = jdbcGenreRepository.findByIdFilm(2L);
        assertThat(genres)
                .isNotNull()
                .hasSize(1)
                .extracting(Genre::getId, Genre::getName)
                .containsExactlyInAnyOrder(
                        tuple(2, "Драма")
                );
    }

}
