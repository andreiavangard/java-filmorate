package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.repository.mappers.FilmRowMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcFilmRepository.class, FilmRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("JdbcFilmRepository")
public class JdbcFilmRepositoryTest {
    public static final long TEST_FILM_ID_1 = 1L;
    public static final long TEST_FILM_ID_2 = 2L;
    private final JdbcFilmRepository filmRepository;

    static Film getTestFilm1() {
        Film film = new Film();
        film.setId(TEST_FILM_ID_1);
        film.setName("film1");
        film.setDescription("descriptionFilm1");
        film.setReleaseDate(LocalDate.of(1982, 03, 12));
        film.setDuration(2);
        MPA mpa = new MPA();
        mpa.setId(1);
        film.setMpa(mpa);
        return film;
    }

    @Test
    @DisplayName("Тест должен находить фильм по ид")
    public void test_should_find_film_by_ID() {
        Film testFilm1 = getTestFilm1();
        Optional<Film> filmOptional = filmRepository.findById(testFilm1.getId());

        assertThat(filmOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(testFilm1);

    }

    @Test
    @DisplayName("Тест должен получить список фильмов")
    public void test_should_get_list_films() {
        Collection<Film> filmList = filmRepository.findAll();

        assertThat(filmList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(2)
                .extracting(Film::getId)
                .contains(TEST_FILM_ID_1);
    }

    @Test
    @DisplayName("Тест должен создать фильм")
    public void test_should_create_film() {
        Film film = new Film();
        film.setName("film3");
        film.setDescription("descriptionFilm3");
        film.setReleaseDate(LocalDate.of(1984, 03, 12));
        film.setDuration(2);
        MPA mpa = new MPA();
        mpa.setId(3);
        film.setMpa(mpa);

        Film newFilm = filmRepository.create(film);

        assertThat(newFilm)
                .isNotNull()
                .satisfies(createdUser -> {
                    assertThat(createdUser.getId()).isNotNull().isPositive();
                    assertThat(createdUser.getName()).isEqualTo("film3");
                    assertThat(createdUser.getDescription()).isEqualTo("descriptionFilm3");
                    assertThat(createdUser.getReleaseDate()).isEqualTo(LocalDate.of(1984, 3, 12));
                });

    }

    @Test
    @DisplayName("Тест должен обновить фильм")
    public void test_should_update_film() {
        Optional<Film> filmOptional = filmRepository.findById(TEST_FILM_ID_1);
        Film film = filmOptional.get();
        film.setName(film.getName() + "_");//.setLogin(user.getLogin()+"_");
        film.setDescription(film.getDescription());//.setName(user.getName()+"_");
        boolean success = filmRepository.update(film);
        assertThat(success).isTrue();
        Optional<Film> updatedUserOptional = filmRepository.findById(TEST_FILM_ID_1);
        assertThat(updatedUserOptional)
                .isPresent()
                .get()
                .extracting(Film::getName, Film::getDescription)
                .contains(film.getName(), film.getDescription());

    }

    @Test
    @DisplayName("Тест должен удалить фильм")
    public void test_should_delete_film() {
        Film film = new Film();
        film.setName("film4");
        film.setDescription("descriptionFilm4");
        film.setReleaseDate(LocalDate.of(1984, 03, 12));
        film.setDuration(2);
        MPA mpa = new MPA();
        mpa.setId(3);
        film.setMpa(mpa);

        Film newFilm = filmRepository.create(film);
        Long idFilm = newFilm.getId();
        boolean success = filmRepository.delete(idFilm);
        assertThat(success).isTrue();

        Optional<Film> filmDelete = filmRepository.findById(idFilm);

        assertThat(filmDelete).isEmpty();

    }

    @Test
    @DisplayName("Тест должен получить список популярных фильмов")
    public void test_should_get_list_popular_films() {
        Collection<Film> filmList = filmRepository.getPopular(1);

        assertThat(filmList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(1)
                .extracting(Film::getId)
                .contains(TEST_FILM_ID_2);
    }

    @Test
    @DisplayName("Тест должен добавить фильму жанр")
    public void test_should_create_genre_film() {

        Set<Genre> genres = new HashSet<>();
        Genre genre = new Genre();
        genre.setId(1);
        genres.add(genre);
        filmRepository.addGenres(1, genres);
        Optional<Film> filmOptional = filmRepository.findById(1L);
        Set<Genre> filmGenre = filmOptional.get().getGenres();

        assertThat(filmGenre)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .first()
                .satisfies(actualGenre -> {
                    assertThat(actualGenre.getId()).isEqualTo(1);
                });
    }

    @Test
    @DisplayName("Тест должен удалить у  фильма жанр")
    public void test_should_delete_genre_film() {
        boolean success = filmRepository.deleteGenre(2L);
        assertThat(success).isTrue();
        Optional<Film> filmOptional = filmRepository.findById(2L);
        Set<Genre> filmGenre = filmOptional.get().getGenres();
        assertThat(filmGenre).isEmpty();
    }

}
