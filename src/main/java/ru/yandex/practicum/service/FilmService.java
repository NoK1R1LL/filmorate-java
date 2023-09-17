package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.FilmStorage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService; // Внедряем UserService

    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film createFilm(Film film) {
        LocalDate releaseDate = film.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (releaseDate != null && releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new IllegalArgumentException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Long id, Film updatedFilm) {
        return filmStorage.updateFilm(id, updatedFilm);
    }

    public Film getFilmById(Long id) {
        Optional<Film> filmOptional = filmStorage.getFilmById(id);
        if (filmOptional.isPresent()) {
            return filmOptional.get();
        } else {
            throw new IllegalArgumentException("Фильм не найден");
        }
    }

    public void deleteFilm(Long id) {
        filmStorage.deleteFilm(id);
    }

    public void likeFilm(Long filmId, Long userId) {
        Film film = getFilmById(filmId);

        // Проверка, что пользователь еще не поставил лайк
        if (!userAlreadyLikedFilm(film, userId)) {
            User user = userService.getUserById(userId);
            film.getLikes().add(user);
        }
    }

    public void unlikeFilm(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        User user = userService.getUserById(userId);
        film.getLikes().remove(user);
    }

    public List<Film> getPopularFilms(int count) {
        // Сортируем фильмы по количеству лайков в убывающем порядке и ограничиваем список указанным количеством
        return filmStorage.getAllFilms()
                .stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private boolean userAlreadyLikedFilm(Film film, Long userId) {
        return film.getLikes()
                .stream()
                .anyMatch(user -> user.getId().equals(userId));
    }
}
