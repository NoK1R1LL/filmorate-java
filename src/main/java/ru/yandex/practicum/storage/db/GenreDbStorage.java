package ru.yandex.practicum.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Genre;
import ru.yandex.practicum.storage.GenreStorage;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteAllGenresById(int filmId) {
        String sqlQuery = "DELETE film_genres WHERE genre_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    @Override
    public Genre getGenreById(int genreId) {
        String sqlQuery = "SELECT * FROM genres WHERE genre_id = ?";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery, genreId);
        if (srs.next()) {
            return new Genre((long)genreId, srs.getString("genre_name"));
        }
        return null;
    }


    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        String sqlQuery = "SELECT * FROM genres ";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery);
        while (srs.next()) {
            Long id = (long) srs.getInt("genre_id"); // Преобразование int в Long
            String name = srs.getString("genre_name");
            genres.add(new Genre(id, name));
        }
        return genres;
    }

}
