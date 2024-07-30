package op.co.Spring_Boot_Task.director;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface DirectorRepository extends JpaRepository<Director, UUID> {
    @Query("SELECT d FROM Director d WHERE d.firstName = ?1")
    List<Director> findDirectorsByFirstName(String firstName);

    @Query("SELECT d FROM Director d WHERE d.lastName = ?1")
    List<Director> findDirectorsByLastName(String lastName);

    @Query("SELECT d FROM Director d WHERE d.email = ?1")
    Optional<Director> findDirectorByEmail(String email);

    @Query("SELECT d FROM Director d WHERE d.gender = ?1")
    List<Director> findDirectorsByGender(Director.Gender gender);

    @Transactional
    @Query("DELETE FROM Director d WHERE d.directorId = ?1")
    int deleteDirectorById(UUID directorId);

    @Transactional
    @Query("UPDATE Director d SET d.firstName = :firstName, d.lastName = :lastName, d.email = :email, d.dob = :dob, d.gender = :gender WHERE d.directorId = :directorId")
    int updateDirectorById(@Param("directorId") UUID directorId, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("dob") LocalDate dob, @Param("gender") Director.Gender gender);
}