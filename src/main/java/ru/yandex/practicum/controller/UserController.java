package ru.yandex.practicum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Получение списка всех пользователей");
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Создание нового пользователя: {}", user);
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        log.info("Обновление пользователя с id={} данными: {}", id, user);
        return userService.updateUser(id, user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        log.info("Получение пользователя по id={}", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("Удаление пользователя с id={}", id);
        userService.deleteUser(id);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<String> addFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId
    ) {
        userService.addFriend(userId, friendId);
        return ResponseEntity.ok("Friend added successfully");
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<String> removeFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId
    ) {
        userService.removeFriend(userId, friendId);
        return ResponseEntity.ok("Friend removed successfully");
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(
            @PathVariable Long userId
    ) {
        return userService.getFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> getCommonFriends(
            @PathVariable Long userId,
            @PathVariable Long otherId
    ) {
        return userService.getCommonFriends(userId, otherId);
    }
}
