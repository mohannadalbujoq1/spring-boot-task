package op.co.Spring_Boot_Task.film;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import op.co.Spring_Boot_Task.actor.ActorFilm;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface FilmRepository extends JpaRepository<Film, UUID> {
    @Query("SELECT f FROM Film f WHERE f.title = ?1")
    Optional<Film> findFilmByTitle(String title);

    @Query("SELECT f FROM Film f WHERE f.genre = ?1")
    List<Film> findFilmsByGenre(String genre);

    @Transactional
    @Query("DELETE FROM Film f WHERE f.filmId = ?1")
    int deleteFilmById(UUID filmId);

    @Transactional
    @Query(value = "INSERT INTO actor_film (film_id, actor_id) VALUES (:filmId, :actorId)", nativeQuery = true)
    int addActorToFilm(@Param("filmId") UUID filmId, @Param("actorId") UUID actorId);
    
    @Transactional
    @Query(value = "DELETE FROM actor_film WHERE film_id = :filmId AND actor_id = :actorId", nativeQuery = true)
    int deleteActorFromFilm(@Param("filmId") UUID filmId, @Param("actorId") UUID actorId);

    @Query("SELECT f FROM Film f")
    List<Film> selectAllFilms();

    @Query("SELECT af FROM ActorFilm af WHERE af.filmId = :filmId")
    List<ActorFilm> selectAllFilmActors(@Param("filmId") UUID filmId);

    @Transactional
    @Query(value = "UPDATE Film f SET f.title = :title, f.releaseDate = :releaseDate, f.genre = :genre, f.duration = :duration, f.directorId = :directorId WHERE f.filmId = :filmId")
    int updateFilmById(@Param("filmId") UUID filmId, @Param("title") String title, @Param("releaseDate") LocalDate releaseDate, @Param("genre") String genre, @Param("duration") Integer duration, @Param("directorId") UUID directorId);

    @Query("SELECT (COUNT(f) > 0) FROM Film f WHERE f.directorId = :directorId")
    boolean isDirectorIdTaken(@Param("directorId") UUID directorId);

    Collection<Film> findFilmsByDirectorId(UUID directorId);


}