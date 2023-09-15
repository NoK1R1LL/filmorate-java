package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    // Хранилище фильмов
    private final List<Film> films = new ArrayList<>();

    // Переменная для уникального идентификатора фильма
    private long nextFilmId = 1;

    // Метод для получения всех фильмов
    public List<Film> getAllFilms() {
        return films;
    }

    // Метод для создания нового фильма
    public Film createFilm(Film film) {
        film.setId(nextFilmId++);
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
}
