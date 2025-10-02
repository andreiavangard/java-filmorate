package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.mappers.UserRowMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import({JdbcUserRepository.class, UserRowMapper.class})
@RequiredArgsConstructor(onConstructor_=@Autowired)
@DisplayName("JdbcUserRepository")
public class JdbcUserRepositoryTest {
    public static final long TEST_USER_ID_1 = 1L;
    public static final long TEST_USER_ID_2 = 2L;
    private final JdbcUserRepository userRepository;

    static User getTestUser1(){
        User user = new User();
        user.setId(TEST_USER_ID_1);
        user.setEmail("email@mail.ru");
        user.setLogin("user1");
        user.setName("test1");
        user.setBirthday(LocalDate.of(1990, 04, 15));
        return user;
    }

    @Test
    @DisplayName("Тест должен находить пользователя по ид")
    public void test_should_find_user_by_ID(){
        User testUser1 = getTestUser1();
        Optional<User> userOptional = userRepository.findById(testUser1.getId());

        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(testUser1);

    }

    @Test
    @DisplayName("Тест должен получить список пользователей")
    public void test_should_get_list_users(){
        Collection<User> userList = userRepository.findAll();

        assertThat(userList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(2)
                .extracting(User::getId)
                .contains(TEST_USER_ID_1);

    }

    @Test
    @DisplayName("Тест должен создать пользователя")
    public void test_should_create_user(){
        User user = new User();
        user.setEmail("email@mail.ru");
        user.setLogin("user3");
        user.setName("test3");
        user.setBirthday(LocalDate.of(1990, 04, 15));

        User newUser = userRepository.create(user);

        assertThat(newUser)
                .isNotNull()
                .satisfies(createdUser -> {
                    assertThat(createdUser.getId()).isNotNull().isPositive();
                    assertThat(createdUser.getEmail()).isEqualTo("email@mail.ru");
                    assertThat(createdUser.getLogin()).isEqualTo("user3");
                    assertThat(createdUser.getName()).isEqualTo("test3");
                    assertThat(createdUser.getBirthday()).isEqualTo(LocalDate.of(1990, 4, 15));
                });

    }

    @Test
    @DisplayName("Тест должен обновить пользователя")
    public void test_should_update_user(){
        Optional<User> userOptional = userRepository.findById(TEST_USER_ID_2);
        User user = userOptional.get();
        user.setLogin(user.getLogin()+"_");
        user.setName(user.getName()+"_");
        boolean success = userRepository.update(user);
        assertThat(success).isTrue();
        Optional<User> updatedUserOptional = userRepository.findById(TEST_USER_ID_2);
        assertThat(updatedUserOptional)
                .isPresent()
                .get()
                .extracting(User::getLogin, User::getName)
                .contains(user.getLogin(), user.getName());

    }


}
