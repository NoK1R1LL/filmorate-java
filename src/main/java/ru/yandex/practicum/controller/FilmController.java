package ru.yandex.practicum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Validated

public class FilmController {

    private final FilmService filmService;
    private final Logger log = LoggerFactory.getLogger(FilmController.class);


    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получение списка всех фильмов");
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Создание нового фильма: {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable Long id, @Valid @RequestBody Film film) {
        log.info("Обновление фильма с id={} данными: {}", id, film);
        return filmService.updateFilm(id, film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        log.info("Получение фильма по id={}", id);
        return filmService.getFilmById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        log.info("Удаление фильма с id={}", id);
        filmService.deleteFilm(id);
    }
}