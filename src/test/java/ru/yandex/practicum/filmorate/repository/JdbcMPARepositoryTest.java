package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.repository.mappers.MPARowMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@JdbcTest
@Import({JdbcMPARepository.class, MPARowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("JdbcMPARepository")
public class JdbcMPARepositoryTest {
    private final JdbcMPARepository jdbcMPARepository;

    @Test
    @DisplayName("Тест должен получить рейтинг по ид")
    public void test_must_be_rated_according_to_id() {
        jdbcMPARepository.findById(1);
        Optional<MPA> mpa = jdbcMPARepository.findById(1);
        assertThat(mpa)
                .isPresent()
                .get()
                .satisfies(m -> {
                    assertThat(m.getId()).isEqualTo(1);
                    assertThat(m.getName()).isEqualTo("G");
                    assertThat(m.getDescription()).isEqualTo("У фильма нет возрастных ограничений");
                });
    }

    @Test
    @DisplayName("Тест должен получить все рейтинги")
    public void test_must_receive_all_ratings() {
        List<MPA> listMpa = jdbcMPARepository.findAll();
        assertThat(listMpa)
                .isNotNull()
                .hasSize(5)
                .extracting(MPA::getId, MPA::getName, MPA::getDescription)
                .containsExactlyInAnyOrder(
                        tuple(1, "G", "У фильма нет возрастных ограничений"),
                        tuple(2, "PG", "Детям рекомендуется смотреть фильм с родителями"),
                        tuple(3, "PG-13", "Детям до 13 лет просмотр не желателен"),
                        tuple(4, "R", "Лицам до 17 лет просматривать фильм можно только в присутствии взрослого"),
                        tuple(5, "NC-17", "Лицам до 18 лет просмотр запрещён")
                );
    }


}
