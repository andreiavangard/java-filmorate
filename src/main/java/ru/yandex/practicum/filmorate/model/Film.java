package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validate.MinimumDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    @NotBlank(message = "Название  должно быть указано")
    private String name;
    @NotBlank(message = "Описание  должно быть заполнено")
    @Size(max = 200, message = "максимальный размер описания 200 символов")
    private String description;
    @MinimumDate(message = "Дата релиза не может быть раньше 28 декабря 1895", value = "1895-12-28")
    @Past(message = "Дата релиза должна быть в прошлом")
    private LocalDate releaseDate;
    @Positive(message = "Длительность должна быть положительной")
    private int duration;
    @Builder.Default
    private Set<Genre> genres = new HashSet<>();
    private MPA mpa;

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

}