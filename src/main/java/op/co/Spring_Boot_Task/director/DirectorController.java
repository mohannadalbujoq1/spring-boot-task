package op.co.Spring_Boot_Task.director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public Director getDirectorById(@PathVariable("directorId") UUID directorId) {
        return directorService.getDirectorById(directorId)
                .orElse(null);
    }

    @PostMapping
    public void addNewDirector(@RequestBody Director director) {
        directorService.addNewDirector(director);
    }

    @PutMapping(path = "{directorId}")
    public void updateDirector(@PathVariable("directorId") UUID directorId, @RequestBody Director director) {
        directorService.updateDirector(directorId, director);
    }

    @DeleteMapping(path = "{directorId}")
    public void deleteDirector(@PathVariable("directorId") UUID directorId) {
        directorService.deleteDirector(directorId);
    }

 
}