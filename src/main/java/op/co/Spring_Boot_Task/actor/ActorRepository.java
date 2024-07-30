package op.co.Spring_Boot_Task.actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface ActorRepository extends JpaRepository<Actor, UUID> {
    @Query("SELECT a FROM Actor a WHERE a.email = ?1")
    Optional<Actor> findActorByEmail(String email);

    @Query("SELECT a FROM Actor a WHERE a.firstName = ?1")
    List<op.co.Spring_Boot_Task.actor.Actor> findActorsByFirstName(String firstName);

    @Transactional
    @Modifying
    @Query("DELETE FROM Actor a WHERE a.actorId = ?1")
    int deleteActorById(UUID actorId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO actor_film (film_id, actor_id) VALUES (:filmId, :actorId)", nativeQuery = true)
    int addActorToFilm(@Param("filmId") UUID filmId, @Param("actorId") UUID actorId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM actor_film WHERE film_id = :filmId AND actor_id = :actorId", nativeQuery = true)
    int deleteActorFromFilm(@Param("filmId") UUID filmId, @Param("actorId") UUID actorId);

    @Query("SELECT a FROM Actor a")
    List<Actor> selectAllActors();

    @Query("SELECT new op.co.Spring_Boot_Task.actor.ActorFilmDTO(af.actorId, af.filmId, a.firstName, a.lastName, f.title) FROM ActorFilm af JOIN Actor a ON af.actorId = a.actorId JOIN Film f ON af.filmId = f.filmId WHERE af.actorId = :actorId")
    List<ActorFilmDTO> selectAllActorFilms(@Param("actorId") UUID actorId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Actor a SET a.firstName = :firstName, a.lastName = :lastName, a.email = :email, a.gender = :gender WHERE a.actorId = :actorId")
    int updateActorById(@Param("actorId") UUID actorId, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("gender") Actor.Gender gender);

    @Query("SELECT (COUNT(a) > 0) FROM Actor a WHERE a.email = :email")
    boolean isEmailTaken(@Param("email") String email);
}