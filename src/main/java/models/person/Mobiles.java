package models.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mobiles {

    @JsonProperty("mobileNumberRelation")
    private String mobileRelationName;

    @JsonProperty("mobileNumber")
    private String mobileNumber;
}
