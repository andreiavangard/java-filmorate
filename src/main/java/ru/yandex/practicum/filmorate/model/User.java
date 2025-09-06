package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //заполняем автоматически
    private Long id;
    @NotBlank(message = "Адрес почты должен быть указан")
    @Email(message = "Неправильный формат электронной почты")
    private String email;
    @NotBlank(message = "Логин должен быть указан")
    private String login;
    //имя может быть пустым, тогда берется из логина
    private String name;
    @NotNull(message = "День рождения должен быть указан")
    @Past(message = "День рождения должен быть в прошлом")
    private LocalDate birthday;
    @Builder.Default
    private Set<Long> friends = new HashSet<>();

    public void setFriend(Long userId) {
        if (!friends.contains(userId)) {
            friends.add(userId);
        }
    }

    public Set<Long> getIdsFriends() {
        return Set.copyOf(friends);
    }

    public void deleteFriend(Long userId) {
        if (friends.contains(userId)) {
            friends.remove(userId);
        }
    }

}