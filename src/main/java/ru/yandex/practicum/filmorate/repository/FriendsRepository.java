package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Friends;

import java.util.Collection;

public interface FriendsRepository {

    boolean addFriend(Long userId, Long friendId);

    boolean deleteFriend(long userId, long friendId);

    Collection<Friends> findFriends(long userId);

}
