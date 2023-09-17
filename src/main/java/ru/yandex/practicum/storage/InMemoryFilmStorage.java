package ru.yandex.practicum.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final List<Film> films = new ArrayList<>();
    private long nextFilmId = 1;

    public List<Film> getAllFilms() {
        return films;
    }

    public Film createFilm(Film film) {
        film.setId(nextFilmId++);
        films.add(film);
        return film;
    }

    public Optional<Film> getFilmById(Long id) {
        return films.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst();
    }

    @Override
    public Film updateFilm(Long id, Film updatedFilm) {
        Optional<Film> existingFilmOptional = getFilmById(id);

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

    @Override
    public void deleteFilm(Long id) {
        films.removeIf(film -> film.getId().equals(id));
    }
}
