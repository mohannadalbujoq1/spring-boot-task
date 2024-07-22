package op.co.Spring_Boot_Task.film;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import op.co.Spring_Boot_Task.actor.ActorFilm;

@Repository
public class FilmDataAccessService {

    private final JdbcTemplate jdbcTemplate;

    public FilmDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("deprecation")
    List<ActorFilm> selectAllFilmActors(UUID filmId) {
        final String sql = "SELECT actor_id, film_id, title, genre, director_id, start_date, end_date, role FROM actor_film WHERE film_id = ?";
        return jdbcTemplate.query(sql, new Object[]{filmId}, mapActorFilmFromDb());
    }

    List<Film> selectAllFilms() {
        final String sql = "SELECT film_id, title, release_date, genre, duration, director_id FROM film";
        return jdbcTemplate.query(sql, mapFilmFromDb());
    }

    public boolean directorExists(UUID directorId) {
        final String sql = "SELECT COUNT(*) FROM director WHERE director_id = ?";
        @SuppressWarnings("deprecation")
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{directorId}, Integer.class);
        return count != null && count > 0;
    }

    public int insertFilm(UUID filmId, Film film) {
        UUID directorId = film.getDirectorId();
        boolean directorExists = directorExists(directorId);
    
        if (!directorExists) {
            throw new IllegalArgumentException("Director with ID " + directorId + " does not exist.");
        }
    
        final String sql = "INSERT INTO film (film_id, title, release_date, genre, duration, director_id) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, filmId, film.getTitle(), film.getReleaseDate(), film.getGenre(), film.getDuration(), film.getDirectorId());
    }
    private RowMapper<ActorFilm> mapActorFilmFromDb() {
        return (resultSet, i) -> {
            UUID actorId = UUID.fromString(resultSet.getString("actor_id"));
            UUID filmId = UUID.fromString(resultSet.getString("film_id"));
            return new ActorFilm(actorId, filmId);
        };
    }

    private RowMapper<Film> mapFilmFromDb() {
        return (resultSet, i) -> {
            UUID filmId = UUID.fromString(resultSet.getString("film_id"));
            String title = resultSet.getString("title");
            LocalDate releaseDate = resultSet.getDate("release_date").toLocalDate();
            String genre = resultSet.getString("genre");
            int duration = resultSet.getInt("duration");
            UUID directorId = UUID.fromString(resultSet.getString("director_id"));
            return new Film(filmId, title, releaseDate, genre, duration, directorId);
        };
    }



    @SuppressWarnings("deprecation")
    public Optional<Film> selectFilmById(UUID filmId) {
        final String sql = "SELECT film_id, title, release_date, genre, duration, director_id FROM film WHERE film_id = ?";
        Film film = jdbcTemplate.queryForObject(sql, new Object[]{filmId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("film_id"));
            String title = resultSet.getString("title");
            LocalDate releaseDate = resultSet.getDate("release_date").toLocalDate();
            String genre = resultSet.getString("genre");
            int duration = resultSet.getInt("duration");
            UUID directorId = UUID.fromString(resultSet.getString("director_id"));
            return new Film(id, title, releaseDate, genre, duration, directorId);
        });

        return Optional.ofNullable(film);
    }

    public int updateFilmById(UUID filmId, Film film) {
        final String sql = "UPDATE film SET title = ?, release_date = ?, genre = ?, duration = ?, director_id = ? WHERE film_id = ?";
        return jdbcTemplate.update(sql, film.getTitle(), film.getReleaseDate(), film.getGenre(), film.getDuration(), film.getDirectorId(), filmId);
    }

    public int deleteFilmById(UUID filmId) {
        final String sql = "DELETE FROM film WHERE film_id = ?";
        return jdbcTemplate.update(sql, filmId);
    }

    public void addActorToFilm(UUID filmId, UUID actorId) {
        final String sql = "INSERT INTO actor_film (actor_id, film_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, actorId, filmId);
    }



@SuppressWarnings("deprecation")
public List<Film> selectAllFilmsForDirectorWithPagination(UUID directorId, Integer start, Integer limit, List<String> retrievedFields, List<String> excludedFields) {
    if (!retrievedFields.contains("film_id")) {
        retrievedFields.add(0, "film_id"); 
    }
    if (!excludedFields.contains("director_id") && !retrievedFields.contains("director_id")) {
        retrievedFields.add("director_id");
    }
    String fields = retrievedFields.stream().filter(field -> !excludedFields.contains(field)).collect(Collectors.joining(", "));
    String sql = String.format("SELECT %s FROM film WHERE director_id = CAST(? AS UUID) LIMIT ? OFFSET ?", fields);
    return jdbcTemplate.query(sql, new Object[]{directorId, limit, start}, mapFilmDb());
}



@SuppressWarnings("deprecation")
public List<Film> selectAllFilmsForActorWithPagination(UUID actorId, Integer start, Integer limit, List<String> retrievedFields, List<String> excludedFields) {
    String fields = retrievedFields.stream().filter(field -> !excludedFields.contains(field)).collect(Collectors.joining(", "));
String sql = String.format("SELECT f.film_id, f.title, f.release_date, f.genre, f.duration ,f.director_id, af.actor_id FROM film f JOIN actor_film af ON f.film_id = af.film_id WHERE af.actor_id = CAST(? AS UUID) LIMIT ? OFFSET ?", fields.replace("releaseDate", "release_date"));    return jdbcTemplate.query(sql, new Object[]{actorId, limit, start}, mapFilmDb());
}

private RowMapper<Film> mapFilmDb() {
    return (resultSet, i) -> {
        UUID id = UUID.fromString(resultSet.getString("film_id"));
        String title = resultSet.getString("title");
        LocalDate releaseDate = resultSet.getDate("release_date").toLocalDate();
        String genre = resultSet.getString("genre");
        int duration = resultSet.getInt("duration");
        UUID directorId = null;
        try {
            directorId = UUID.fromString(resultSet.getString("director_id"));
        } catch (SQLException e) {
        }
        return new Film(id, title, releaseDate, genre, duration, directorId);
    };
}

}
