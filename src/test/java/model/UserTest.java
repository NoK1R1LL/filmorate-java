package model;

import ru.yandex.practicum.model.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        // Очистка данных перед каждым тестом, если необходимо
        restTemplate.delete("/users");
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        User createdUser = response.getBody();
        assertNotNull(createdUser.getId());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("testuser", createdUser.getLogin());
        assertEquals("Test User", createdUser.getName());
        assertEquals(LocalDate.of(1990, 1, 1), createdUser.getBirthday());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        ResponseEntity<User> createResponse = restTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        User createdUser = createResponse.getBody();

        User updatedUser = new User();
        updatedUser.setId(createdUser.getId());
        updatedUser.setEmail("updated@example.com");
        updatedUser.setLogin("updateduser");
        updatedUser.setName("Updated User");
        updatedUser.setBirthday(LocalDate.of(1995, 2, 2));

        ResponseEntity<User> updateResponse = restTemplate.postForEntity("/users", updatedUser, User.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        User updatedResultUser = updateResponse.getBody();
        assertEquals(createdUser.getId(), updatedResultUser.getId());
        assertEquals("updated@example.com", updatedResultUser.getEmail());
        assertEquals("updateduser", updatedResultUser.getLogin());
        assertEquals("Updated User", updatedResultUser.getName());
        assertEquals(LocalDate.of(1995, 2, 2), updatedResultUser.getBirthday());
    }
}
