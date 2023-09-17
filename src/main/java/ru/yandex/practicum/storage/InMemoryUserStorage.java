package ru.yandex.practicum.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final List<User> users = new ArrayList<>();
    private long nextUserId = 1;

    public List<User> getAllUsers() {
        return users;
    }

    public User createUser(User user) {
        user.setId(nextUserId++);
        users.add(user);
        return user;
    }

    public Optional<User> getUserById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = getUserById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Обновление информации о пользователе
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setLogin(updatedUser.getLogin());
            existingUser.setName(updatedUser.getName());
            existingUser.setBirthday(updatedUser.getBirthday());
            return existingUser;
        } else {
            throw new IllegalArgumentException("Пользователь не найден");
        }
    }

    @Override
    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}
