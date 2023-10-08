package ru.yandex.practicum.storage.db;

import ru.yandex.practicum.storage.*;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Repository
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO users (email, login, name, birthday, friends) VALUES (?, ?, ?, ?, ?)";
        String joinedFriends = user.getFriends().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), joinedFriends);
        return user;
    }


    @Override
    public Optional<User> getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserMapper(), id);
        if (users.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(users.get(0));
        }
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        StringJoiner friendsJoiner = new StringJoiner(",");
        updatedUser.getFriends().forEach(friend -> friendsJoiner.add(String.valueOf(friend)));

        String sql = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ?, friends = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedUser.getEmail(), updatedUser.getLogin(), updatedUser.getName(), updatedUser.getBirthday(), friendsJoiner.toString(), id);

        return updatedUser;
    }


    @Override
    public void deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
