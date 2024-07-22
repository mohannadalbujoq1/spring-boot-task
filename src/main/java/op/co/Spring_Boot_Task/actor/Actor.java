package op.co.Spring_Boot_Task.actor;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Actor {
    private final UUID actorId;
    @NotBlank
    private final String firstName;
    @NotBlank
    private final String lastName;
    @Email
    private final String email;
    @NotNull
    private final Gender gender;

    public Actor(
    @JsonProperty("actorId") UUID actorId,
    @JsonProperty("firstName") String firstName,
    @JsonProperty("lastName") String lastName,
    @JsonProperty("email") String email,
    @JsonProperty("gender") Gender gender) {
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

}