package models.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonResponse {

    @JsonProperty("id")
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    // Add all response fields

    // Getters and setters

}
