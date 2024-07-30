package op.co.Spring_Boot_Task.director;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import op.co.Spring_Boot_Task.film.Film;

@RestController
@RequestMapping("/api/director")
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public List<Director> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping(path = "{directorId}")
    public Director getDirectorById(@PathVariable UUID directorId) {
        return directorService.getDirectorById(directorId)
                .orElse(null);
    }
    
  

    @PostMapping
    public void addNewDirector(@RequestBody Director director) {
        directorService.addNewDirector(director);
    }

    @PutMapping(path = "{directorId}")
    public void updateDirector(@PathVariable UUID directorId, @RequestBody Director director) {
        directorService.updateDirector(directorId, director);
    }

    @DeleteMapping(path = "{directorId}")
    public void deleteDirector(@PathVariable UUID directorId) {
        directorService.deleteDirector(directorId);
    }
}