package ru.yandex.practicum.storage.mapper;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setLogin(resultSet.getString("login"));
        user.setName(resultSet.getString("name"));
        user.setBirthday(resultSet.getDate("birthday").toLocalDate());

        String friendsString = resultSet.getString("friends");
        if (friendsString != null && !friendsString.isEmpty()) {
            String[] friendIds = friendsString.split(",");
            Set<Long> friendIdsSet = Arrays.stream(friendIds)
                    .map(Long::parseLong)
                    .collect(Collectors.toSet());
            user.setFriends(friendIdsSet);
        } else {
            user.setFriends(Collections.emptySet());
        }

        return user;
    }
}