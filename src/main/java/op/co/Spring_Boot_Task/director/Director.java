package op.co.Spring_Boot_Task.director;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name = "director")
public class Director {
    @Id
    @Column(name = "director_id")
    private UUID directorId;
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @Email
    @Column(name = "email")
    private String email;
    @NotNull
    @Column(name = "dob")
    private LocalDate dob;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    public Director() {}

    public Director(
    @JsonProperty("directorId") UUID directorId,
    @JsonProperty("firstName") String firstName,
    @JsonProperty("lastName") String lastName,
    @JsonProperty("email") String email,
    @JsonProperty("dob") LocalDate dob,
    @JsonProperty("gender") Gender gender) {
        this.directorId = directorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
    }



    public UUID getDirectorId() {
        return directorId;
    }

    public LocalDate getDob() {
        return dob;
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

    public Gender getGender(){
        return gender;
    }
    @Override
    public String toString() {
        return "Director{" +
                "directorId=" + directorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public enum Gender {
        MALE, FEMALE
    }
    public void setDirectorId(UUID directorId) {
        this.directorId = directorId;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setGender(Gender female) {
        this.gender = female;
    }
    
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
  
}