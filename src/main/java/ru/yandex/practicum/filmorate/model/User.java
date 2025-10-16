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
import java.util.HashMap;
import java.util.Map;
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
    private Map<Long, Boolean> friends = new HashMap<>();

    public void setFriend(Long userId) {
        if (!friends.containsKey(userId)) {
            friends.put(userId, false);
        }
    }

    public Set<Long> getIdsFriends() {
        return Map.copyOf(friends).keySet();
    }

    public void deleteFriend(Long userId) {
        if (friends.containsKey(userId)) {
            friends.remove(userId);
        }
    }

}