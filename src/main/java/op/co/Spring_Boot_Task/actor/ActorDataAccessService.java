package op.co.Spring_Boot_Task.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import op.co.Spring_Boot_Task.actor.Actor.Gender;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ActorDataAccessService {

    private static final Logger logger = LoggerFactory.getLogger(ActorDataAccessService.class);

    private final JdbcTemplate jdbcTemplate;


   

   
    @Autowired
    public ActorDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

  
  
    @SuppressWarnings("deprecation")
    public List<ActorFilm> selectAllActorFilms(UUID actorId) {
        final String sql = "SELECT actor.actor_id, actor.first_name, actor.last_name, film.film_id, film.title " + 
                           "FROM actor " +
                           "JOIN actor_film ON actor.actor_id = actor_film.actor_id " +
                           "JOIN film ON actor_film.film_id = film.film_id " +
                           "WHERE actor.actor_id = CAST(? AS UUID)";
    
        return jdbcTemplate.query(sql, new Object[]{actorId}, new RowMapper<ActorFilm>() {
            @Override
            public ActorFilm mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ActorFilm(
                    UUID.fromString(rs.getString("actor_id")),
                    UUID.fromString(rs.getString("film_id")),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("title")
                );
            }
        });
    }

    @SuppressWarnings("deprecation")
    public Optional<Actor> selectActorById(UUID actorId) {
        final String sql = "SELECT actor_id, first_name, last_name, email, gender FROM actor WHERE actor_id = CAST(? AS UUID)";
        try {
            Actor actor = jdbcTemplate.queryForObject(sql, new Object[]{actorId.toString()}, this::mapActorFromDb);
            return Optional.ofNullable(actor);
        } catch (DataAccessException e) {
            logger.error("Error selecting actor by ID: " + actorId, e);
            return Optional.empty();
        }
    }

    public int deleteActorById(UUID actorId) {
        String sql = "DELETE FROM actor WHERE actor_id = CAST(? AS UUID)";
        return jdbcTemplate.update(sql, actorId.toString());
    }

    public int insertActor(UUID actorId, Actor actor) {
        final String sql = "INSERT INTO actor (actor_id, first_name, last_name, email, gender) VALUES (CAST(? AS UUID), ?, ?, ?, ?::gender_enum)";
        return jdbcTemplate.update(sql, actorId.toString(), actor.getFirstName(), actor.getLastName(), actor.getEmail(), actor.getGender().getDbValue());
    }

    public int updateActorById(UUID actorId, Actor actor) {
        final String sql = "UPDATE actor SET first_name = ?, last_name = ?, email = ?, gender = ?::gender_enum WHERE actor_id = CAST(? AS UUID)";
        return jdbcTemplate.update(sql, actor.getFirstName(), actor.getLastName(), actor.getEmail(), actor.getGender().getDbValue(), actorId.toString());
    }

    private Actor mapActorFromDb(ResultSet resultSet, int rowNum) throws SQLException {
        UUID actorId = UUID.fromString(resultSet.getString("actor_id"));
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        Gender gender = Gender.fromDbValue(resultSet.getString("gender"));
        return new Actor(actorId, firstName, lastName, email, gender);
    }

    @SuppressWarnings("deprecation")
    public boolean isEmailTaken(String email) {
        final String sql = "SELECT EXISTS (SELECT 1 FROM actor WHERE email = ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, (resultSet, i) -> resultSet.getBoolean(1));
    }

    
 


    public int addActorToFilm(UUID filmId, UUID actorId) {
        final String sql = "INSERT INTO actor_film (film_id, actor_id) VALUES (CAST(? AS UUID), CAST(? AS UUID))";
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, filmId, java.sql.Types.OTHER);
            ps.setObject(2, actorId, java.sql.Types.OTHER);
            return ps;
        });
    }
    public int deleteActorFromFilm(UUID filmId, UUID actorId) {
        final String sql = "DELETE FROM actor_film WHERE film_id = CAST(? AS UUID) AND actor_id = CAST(? AS UUID)";
        return jdbcTemplate.update(sql, filmId.toString(), actorId.toString());
    }


    public List<Actor> selectAllActors() {
        final String sql = "SELECT actor_id, first_name, last_name, email, gender FROM actor";
        return jdbcTemplate.query(sql, this::mapActorFromDb);
    }
    
}