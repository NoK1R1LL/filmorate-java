package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final List<Genre> genres = new ArrayList<>();
    private int nextGenreId = 1;

    public List<Genre> getAllGenres() {
        return genres;
    }

    public Optional<Genre> getGenreById(Long id) {
        return genres.stream().filter(genre -> genre.getId().equals(id)).findFirst();
    }

    public Genre createGenre(Genre genre) {
        genre.setId((long) nextGenreId);
        nextGenreId++;
        genres.add(genre);
        return genre;
    }
}
