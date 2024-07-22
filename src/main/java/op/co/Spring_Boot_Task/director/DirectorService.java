package op.co.Spring_Boot_Task.director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import op.co.Spring_Boot_Task.film.Film;
import op.co.Spring_Boot_Task.film.FilmDataAccessService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DirectorService {
    private final DirectorDataAccessService directorDataAccessService;
    private final FilmDataAccessService filmDataAccessService;

    @Autowired
    public DirectorService(DirectorDataAccessService directorDataAccessService, FilmDataAccessService filmDataAccessService) {
        this.directorDataAccessService = directorDataAccessService;
        this.filmDataAccessService = filmDataAccessService;
    }
    public List<Director> getAllDirectors() {
        return directorDataAccessService.selectAllDirectors();
    }

    public Optional<Director> getDirectorById(UUID directorId) {
        return directorDataAccessService.selectDirectorById(directorId);
    }

    public int addNewDirector(Director director) {
        UUID newDirectorId = UUID.randomUUID();
        return directorDataAccessService.insertDirector(newDirectorId, director);
    }

    public int updateDirector(UUID directorId, Director director) {
        return directorDataAccessService.updateDirectorById(directorId, director);
    }

    public int deleteDirector(UUID directorId) {
        return directorDataAccessService.deleteDirectorById(directorId);
    }



    
    private Map<String, Object> convertFilmToMap(Film film) {
        Map<String, Object> map = new HashMap<>();
        map.put("filmId", ((Film) film).getFilmId());
        map.put("title", ((Film) film).getTitle());
        map.put("releaseDate", ((Film) film).getReleaseDate());
        map.put("genre", ((Film) film).getGenre());
        map.put("duration", ((Film) film).getDuration());
        map.put("directorId", ((Film) film).getDirectorId());
        return map;
    }
}