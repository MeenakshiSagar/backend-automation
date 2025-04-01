package models.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonRequest {

    //Mandatory form fields
    private Object levelName;
    private Object level;
    private Object dataType;
    private Object dataUnit;
    private Object subUnit;

    private String name;
    private String relationName;
    private String phoneNumber;
    private int designation;
    private String primaryMemberID;
    private String age;
    private String mobilesRelationName;
    private String mobilesPhoneNumber;
    private String addressesTypeOfAddress;
    private String addressesFlatNumber;
    private String addressesArea;
    private String addressesCity;
    private String addressesPinCode;
    private String addressesState;
    private String whatsappNumber;
    private String stdCode;
    private String landlineNumber;
    private String category;
    private String caste;
    private String email;
    private String dob;
    private String anniversary;
    private String fullAddress;
    private String village;
    private String taluka;
    private String district;
    private String pinCode;
    private String education;
    private String profession;
    private String bike;
    private String car;
    private String assemblyConstituency;
    private String booth;
    private String voterID;
    private String pannaNumber;


    // Getters and setters


}
