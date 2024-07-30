package op.co.Spring_Boot_Task.director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import op.co.Spring_Boot_Task.film.Film;
import op.co.Spring_Boot_Task.film.FilmRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import java.time.LocalDate;
@Service
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final FilmRepository filmRepository;


    @Autowired
    public DirectorService(DirectorRepository directorRepository, FilmRepository filmRepository) {
        this.directorRepository = directorRepository;
        this.filmRepository = filmRepository;
    }


    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    public Optional<Director> getDirectorById(UUID directorId) {
        return directorRepository.findById(directorId);
    }

    public Director addNewDirector(Director director) {
        director.setDirectorId(UUID.randomUUID()); 
        return directorRepository.save(director);
    }

    public Director updateDirector(UUID directorId, Director directorDetails) {
        return directorRepository.findById(directorId).map(director -> {
            director.setFirstName(directorDetails.getFirstName());
            director.setLastName(directorDetails.getLastName());
            director.setEmail(directorDetails.getEmail());
            director.setDob(directorDetails.getDob());
            director.setGender(directorDetails.getGender());
            return directorRepository.save(director);
        }).orElseGet(() -> {
            directorDetails.setDirectorId(directorId);
            return directorRepository.save(directorDetails);
        });
    }

    public void deleteDirector(UUID directorId) {
        directorRepository.deleteById(directorId);
    }

}