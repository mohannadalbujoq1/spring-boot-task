package op.co.Spring_Boot_Task.film;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import op.co.Spring_Boot_Task.actor.Actor;

@Entity
@Table(name = "film")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID filmId;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private LocalDate releaseDate;

    @NotBlank
    @Column(nullable = false)
    private String genre;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer duration;

    @NotNull
    @Column(nullable = false)
    private UUID directorId;

    public Film() {
    }

    @ManyToMany
    @JoinTable(
        name = "actor_film",
        joinColumns = @JoinColumn(name = "film_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors;

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

    public void setFilmId(UUID filmId) {
        this.filmId = filmId;
    }
    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }
    public void setTitle(String title) {
    this.title = title;
}

public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
}

public void setGenre(String genre) {
    this.genre = genre;
}

public void setDuration(Integer duration) {
    this.duration = duration;
}

public void setDirectorId(UUID directorId) {
    this.directorId = directorId;
}
}