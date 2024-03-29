package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;

@Repository("filmStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreService genreService;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreService genreService){
        this.jdbcTemplate = jdbcTemplate;
        this.genreService = genreService;
    }

    @Override
    public Optional<Film> loadFilm(long id) {
        String sqlQuery =
                "SELECT f.id, " +
                        "f.name, " +
                        "f.description, " +
                        "f.release_date, " +
                        "f.duration, " +
                        "f.mpa_id, " +
                        "m.name mpa " +
                "FROM films f " +
                "JOIN mpa m" +
                "    ON m.id = f.mpa_id " +
                "WHERE f.id = ?;";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper(genreService), id).stream().findAny();
    }

    @Override
    public long saveFilm(Film film) {
        String sqlQuery = "INSERT INTO films (name, description, release_date, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setLong(4, film.getDuration());
            statement.setLong(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void updateFilm(Film film) {
        String sqlQuery = "UPDATE films " +
                "SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(
                sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );
    }

    @Override
    public List<Film> loadFilms() {
        String sqlQuery =
                "SELECT f.id, " +
                        "f.name, " +
                        "f.description, " +
                        "f.release_date, " +
                        "f.duration, " +
                        "f.mpa_id, " +
                        "m.name mpa, " +
                "FROM films f " +
                "JOIN mpa m" +
                "    ON m.id = f.mpa_id;";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper(genreService));
    }

    @Override
    public void saveRatingPoint(long filmId, long userId) {
        String sqlQuery = "INSERT INTO films_ratings (film_id, user_id) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void deleteRatingPoint(long filmId, long userId) {
        String sqlQuery = "DELETE FROM films_ratings WHERE film_id = ? AND user_id = ?;";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public boolean hasFilmRatingFromUser(long filmId, long userId) {
        String sqlQuery = "SELECT COUNT(user_id) FROM films_ratings WHERE film_id = ? AND user_id = ?;";
        int rating = jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId, userId);
        return rating > 0;
    }

    @Override
    public List<Film> loadPopularFilms(long count) {
        String sqlQuery =
                "SELECT f.id, " +
                        "f.name, " +
                        "f.description, " +
                        "f.release_date, " +
                        "f.duration, " +
                        "f.mpa_id, " +
                        "m.name mpa, " +
                "FROM films f " +
                "JOIN mpa m" +
                "    ON m.id = f.mpa_id " +
                "LEFT JOIN (SELECT film_id, " +
                "      COUNT(user_id) rating " +
                "      FROM films_ratings " +
                "      GROUP BY film_id " +
                ") r ON f.id =  r.film_id " +
                "ORDER BY r.rating DESC " +
                "LIMIT ?;";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper(genreService), count);
    }
}
