package op.co.Spring_Boot_Task.film;

import op.co.Spring_Boot_Task.actor.Actor;
import op.co.Spring_Boot_Task.actor.ActorFilm;
import op.co.Spring_Boot_Task.actor.ActorFilmDTO;
import op.co.Spring_Boot_Task.actor.ActorFilmId;
import op.co.Spring_Boot_Task.actor.ActorRepository;
import op.co.Spring_Boot_Task.actor.ActorFilmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FilmService {

  
    private final FilmRepository filmRepository;
    private final ActorRepository actorRepository;
    private final ActorFilmRepository actorFilmRepository;
    public FilmService(FilmRepository filmRepository, ActorRepository actorRepository, ActorFilmRepository actorFilmRepository) {
        this.filmRepository = filmRepository;
        this.actorRepository = actorRepository;
        this.actorFilmRepository = actorFilmRepository;
    }

    public List<Film> getAllFilms(Integer start, Integer limit, List<String> retrievedFields, List<String> excludedFields) {
        start = start == null ? 0 : start;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        List<Film> films = filmRepository.findAll()
                                         .stream()
                                         .skip(start)
                                         .limit(limit)
                                         .collect(Collectors.toList());
        return filterFilmsFields(films, retrievedFields, excludedFields);
    }

    public void addNewFilm(Film film) {
        filmRepository.save(film);
    }

    void addNewFilm(UUID filmId, Film film) {
        film.setFilmId(filmId);
        filmRepository.save(film);
    }

    public Optional<Film> getFilmById(UUID filmId) {
        return filmRepository.findById(filmId);
    }
    
    public void updateFilm(UUID filmId, Film film) {
        film.setFilmId(filmId);
        filmRepository.save(film);
    }
    
    public void deleteFilm(UUID filmId) {
        filmRepository.deleteById(filmId);
    }


    public List<ActorFilmDTO> getAllActorsForFilm(UUID filmId) {
        List<ActorFilm> actorFilms = filmRepository.selectAllFilmActors(filmId);
        return actorFilms.stream()
                         .map(this::convertToDTO)
                         .collect(Collectors.toList());
    }
    
    public ActorFilmDTO addActorToFilm(@Valid ActorFilm actorFilm) {
        if (filmRepository.findById(actorFilm.getFilmId()).isPresent() && actorRepository.findById(actorFilm.getActorId()).isPresent()) {
            ActorFilm savedActorFilm = actorFilmRepository.save(actorFilm); 
            return convertToDTO(savedActorFilm);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film or Actor not found");
        }
    }

private ActorFilmDTO convertToDTO(ActorFilm actorFilm) {
    Optional<Actor> actor = actorRepository.findById(actorFilm.getActorId());
    Optional<Film> film = filmRepository.findById(actorFilm.getFilmId());
    if (actor.isPresent() && film.isPresent()) {
        return new ActorFilmDTO(
            actorFilm.getActorId(),
            actorFilm.getFilmId(),
            actor.get().getFirstName(),
            actor.get().getLastName(),
            film.get().getTitle()
        );
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor or Film not found");
    }
}

public void deleteActorFromFilm(UUID actorId, UUID filmId) {
    Optional<ActorFilm> actorFilm = actorFilmRepository.findById(new ActorFilmId(actorId, filmId));
    if (actorFilm.isPresent()) {
        actorFilmRepository.delete(actorFilm.get());
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ActorFilm mapping not found");
    }
}
public List<ActorFilm> getAllActorFilms() {
    return actorFilmRepository.findAll();
}

public List<ActorFilmDTO> getAllFilmsForActor(UUID actorId) {
    List<ActorFilm> actorFilms = actorFilmRepository.findAllByActorId(actorId);
    return actorFilms.stream()
                     .map(this::convertToDTO)
                     .collect(Collectors.toList());
}

public ActorFilmDTO updateActorFilm(UUID actorId, UUID filmId, @Valid ActorFilm updatedActorFilm) {
    Optional<ActorFilm> existingActorFilm = actorFilmRepository.findById(new ActorFilmId(actorId, filmId));
    if (existingActorFilm.isPresent()) {
        ActorFilm savedActorFilm = actorFilmRepository.save(updatedActorFilm);
        return convertToDTO(savedActorFilm);
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ActorFilm mapping not found");
    }
}


private Film filterFields(Film film, List<String> fields, boolean include) {
    Film filteredFilm = new Film();
    for (Field field : Film.class.getDeclaredFields()) {
        field.setAccessible(true);
        try {
            if (include && fields.contains(field.getName())) {
                field.set(filteredFilm, field.get(film));
            } else if (!include && !fields.contains(field.getName())) {
                field.set(filteredFilm, field.get(film));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    return filteredFilm;
}



private List<Film> filterFilmsFields(List<Film> films, List<String> retrievedFields, List<String> excludedFields) {
    return films.stream().map(film -> {
        Film filteredFilm = new Film();
        Field[] fields = Film.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                boolean isRetrieved = retrievedFields == null || retrievedFields.isEmpty() || retrievedFields.contains(field.getName());
                boolean isExcluded = excludedFields != null && excludedFields.contains(field.getName());
                if (isRetrieved && !isExcluded) {
                    Object value = field.get(film);
                    if (value != null) {
                        field.set(filteredFilm, value);
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return filteredFilm;
    }).collect(Collectors.toList());
}

}

