package model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createFilm_shouldReturnCreatedFilm() {
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("This is a test film");
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(120);

        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("201 CREATED", response.getStatusCode().toString());
        Film createdFilm = response.getBody();
        assertEquals("Test Film", createdFilm.getName());
        assertEquals("This is a test film", createdFilm.getDescription());
        assertEquals(LocalDate.of(2022, 1, 1), createdFilm.getReleaseDate());
        assertEquals(120, createdFilm.getDuration());
    }

    @Test
    void updateFilm_shouldReturnUpdatedFilm() {
        // Создайте фильм
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("This is a test film");
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(120);

        ResponseEntity<Film> createResponse = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals("201 CREATED", createResponse.getStatusCode().toString());
        Film createdFilm = createResponse.getBody();

        // Обновите фильм
        Film updatedFilm = new Film();
        updatedFilm.setId(createdFilm.getId());
        updatedFilm.setName("Updated Film");
        updatedFilm.setDescription("This is an updated film");
        updatedFilm.setReleaseDate(LocalDate.of(2023, 2, 1));
        updatedFilm.setDuration(150);

        HttpEntity<Film> entity = new HttpEntity<>(updatedFilm);
        ResponseEntity<Film> updateResponse = restTemplate.exchange("/films/" + createdFilm.getId(), HttpMethod.PUT, entity, Film.class);

        assertEquals("200 OK", updateResponse.getStatusCode().toString());
        Film updatedResultFilm = updateResponse.getBody();
        assertEquals("Updated Film", updatedResultFilm.getName());
        assertEquals("This is an updated film", updatedResultFilm.getDescription());
        assertEquals(LocalDate.of(2023, 2, 1), updatedResultFilm.getReleaseDate());
        assertEquals(150, updatedResultFilm.getDuration());
    }
}
