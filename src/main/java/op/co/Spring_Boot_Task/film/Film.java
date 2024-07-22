package op.co.Spring_Boot_Task.film;

import java.time.LocalDate;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Film {
    private final UUID filmId;
    @NotBlank
    private final String title;
    @NotNull
    private final LocalDate releaseDate;
    @NotBlank
    private final String genre;
    @NotNull
    @Min(1) 
    private final Integer duration;
    @NotNull
    private final UUID directorId; 
    
    public Film(
        @JsonProperty("filmId") UUID filmId,
        @JsonProperty("title") String title,
        @JsonProperty("releaseDate") LocalDate releaseDate,
        @JsonProperty("genre") String genre,
        @JsonProperty("duration") Integer duration,
        @JsonProperty("directorId") UUID directorId) { 
        this.filmId = filmId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.directorId = directorId; 
    }
    

public UUID getFilmId() {
    return filmId;
}

public String getTitle() {
    return title;
}

public LocalDate getReleaseDate() {
    return releaseDate;
}

public String getGenre() {
    return genre;
}

public Integer getDuration() {
    return duration;
}

public UUID getDirectorId() {
    return directorId;
}

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", directorId=" + directorId +
                '}';
    }
}