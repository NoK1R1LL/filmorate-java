package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Хранилище пользователей
    private final List<User> users = new ArrayList<>();

    // Переменная для уникального идентификатора пользователя
    private long nextUserId = 1;

    // Метод для получения всех пользователей
    public List<User> getAllUsers() {
        return users;
    }

    // Метод для создания нового пользователя
    public User createUser(User user) {
        user.setId(nextUserId++);
        users.add(user);
        return user;
    }

    // Метод для обновления информации о пользователе
    public User updateUser(Long id, User updatedUser) {
        // Поиск существующего пользователя по идентификатору
        Optional<User> existingUserOptional = users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

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

    // Метод для получения информации о пользователе по идентификатору
    public User getUserById(Long id) {
        // Поиск пользователя по идентификатору
        Optional<User> userOptional = users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new IllegalArgumentException("Пользователь не найден");
        }
    }

    // Метод для удаления пользователя по идентификатору
    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}

