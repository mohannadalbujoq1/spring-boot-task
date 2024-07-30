package op.co.Spring_Boot_Task.actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActorFilmRepository extends JpaRepository<ActorFilm, ActorFilmId> {
    List<ActorFilm> findAllByActorId(UUID actorId);

    @Override
    Optional<ActorFilm> findById(ActorFilmId id);
}