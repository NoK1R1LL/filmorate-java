package ru.yandex.practicum.storage;

import ru.yandex.practicum.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> getAllFilms();
    Film createFilm(Film film);
    Optional<Film> getFilmById(Long id);
    Film updateFilm(Long id, Film updatedFilm);
    void deleteFilm(Long id);
}
