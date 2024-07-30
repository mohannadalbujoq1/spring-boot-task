package op.co.Spring_Boot_Task.actor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.persistence.ManyToMany;
import op.co.Spring_Boot_Task.film.Film;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID actorId;

       @ManyToMany(mappedBy = "actors")
    private Set<Film> films;
    
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Actor() {
    }

    public Actor(UUID actorId, String firstName, String lastName, String email, Gender gender) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }


    public UUID getActorId() {
        return actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");

    private final String dbValue;

    Gender(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static Gender fromDbValue(String dbValue) {
        for (Gender gender : Gender.values()) {
            if (gender.getDbValue().equalsIgnoreCase(dbValue)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown database value: " + dbValue);
    }
}
    @Override
    public String toString() {
        return "Actor{" +
                "actorId=" + actorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender +
                '}';

    }

    public void setActorId(UUID actorId) {
        this.actorId = actorId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public void setFilms(Set<Film> films) {
        this.films = films;
    }
}