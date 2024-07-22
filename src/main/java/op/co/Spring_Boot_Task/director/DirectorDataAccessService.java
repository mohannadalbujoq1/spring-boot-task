package op.co.Spring_Boot_Task.director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DirectorDataAccessService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DirectorDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    int deleteDirectorById(UUID directorId) {
        final String sql = "DELETE FROM director WHERE director_id = CAST(? AS UUID)";
    return jdbcTemplate.update(sql, directorId);
    }


    List<Director> selectAllDirectors() {
        final String sql = "SELECT director_id, first_name, last_name, email, dob, gender FROM director";
        return jdbcTemplate.query(sql, mapDirectorFromDb());
    }


    private RowMapper<Director> mapDirectorFromDb() {
        return (resultSet, rowNum) -> {
            UUID directorId = UUID.fromString(resultSet.getString("director_id"));
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            LocalDate dob = resultSet.getDate("dob").toLocalDate(); 
            Director.Gender gender = Director.Gender.valueOf(resultSet.getString("gender"));
            return new Director(directorId, firstName, lastName, email, dob, gender); 
        };
    }

    @SuppressWarnings("deprecation")
    Optional<Director> selectDirectorById(UUID directorId) {
        final String sql = "SELECT director_id, first_name, last_name, email, dob, gender FROM director WHERE director_id = CAST(? AS UUID)";
        Director director = jdbcTemplate.queryForObject(sql, new Object[]{directorId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("director_id"));
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            LocalDate dob = resultSet.getDate("dob").toLocalDate(); 
            Director.Gender gender = Director.Gender.valueOf(resultSet.getString("gender"));
            return new Director(id, firstName, lastName, email, dob, gender); 
        });
        return Optional.ofNullable(director);
    }

    int insertDirector(UUID directorId, Director director) {
        final String sql = "INSERT INTO director (director_id, first_name, last_name, email, dob, gender) VALUES (CAST(? AS UUID), ?, ?, ?, ?, ?::gender_enum)";
        return jdbcTemplate.update(sql, directorId, director.getFirstName(), director.getLastName(), director.getEmail(), director.getDob(), director.getGender().name());
    }

    int updateDirectorById(UUID directorId, Director director) {
        final String sql = "UPDATE director SET first_name = ?, last_name = ?, email = ?, dob = ?, gender = ?::gender_enum WHERE director_id = CAST(? AS UUID)";
        return jdbcTemplate.update(sql, director.getFirstName(), director.getLastName(), director.getEmail(), director.getDob(), director.getGender().name(), directorId);
    }


}