package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Film;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    // Хранилище фильмов
    private final List<Film> films = new ArrayList<>();

    // Переменная для уникального идентификатора фильма
    private Long idGenerator = 1L;

    // Метод для получения всех фильмов
    public List<Film> getAllFilms() {
        return films;
    }

    // Метод для создания нового фильма
    public Film createFilm(Film film) {
        // Преобразование Date в LocalDate
        LocalDate releaseDate = film.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Проверка даты релиза
        if (releaseDate != null && releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new IllegalArgumentException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        film.setId(generateId());
        films.add(film);
        return film;
    }

    // Метод для обновления информации о фильме
    public Film updateFilm(Long id, Film updatedFilm) {
        // Поиск существующего фильма по идентификатору
        Optional<Film> existingFilmOptional = films.stream()
                .filter(film -> film.getId().equals(id))
                .findFirst();

        if (existingFilmOptional.isPresent()) {
            Film existingFilm = existingFilmOptional.get();
            // Обновление информации о фильме
            existingFilm.setName(updatedFilm.getName());
            existingFilm.setDescription(updatedFilm.getDescription());
            existingFilm.setReleaseDate(updatedFilm.getReleaseDate());
            existingFilm.setDuration(updatedFilm.getDuration());
            return existingFilm;
        } else {
            throw new IllegalArgumentException("Фильм не найден");
        }
    }

    // Метод для получения информации о фильме по идентификатору
    public Film getFilmById(Long id) {
        // Поиск фильма по идентификатору
        Optional<Film> filmOptional = films.stream()
                .filter(film -> film.getId().equals(id))
                .findFirst();

        if (filmOptional.isPresent()) {
            return filmOptional.get();
        } else {
            throw new IllegalArgumentException("Фильм не найден");
        }
    }

    // Метод для удаления фильма по идентификатору
    public void deleteFilm(Long id) {
        films.removeIf(film -> film.getId().equals(id));
    }

    private Long generateId() {
        return idGenerator++;
    }
}
