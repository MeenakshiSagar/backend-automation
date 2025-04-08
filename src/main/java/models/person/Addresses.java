package models.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Addresses {

    @JsonProperty("save_as")
    private String typeOfAddress;

    @JsonProperty("flat")
    private String flatNumber;

    @JsonProperty("area")
    private String area;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pin_code")
    private String pinCode;

    @JsonProperty("town_city")
    private String townCity;

}
