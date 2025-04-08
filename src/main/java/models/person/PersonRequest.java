package models.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonRequest {

    /**
     * mandatory form fields
     */
    @JsonProperty("level_name")
    private Object levelName;

    @JsonProperty("level")
    private Object level;

    @JsonProperty("type")
    private Object dataType;

    @JsonProperty("unit")
    private Object dataUnit;

    @JsonProperty("sub_unit")
    private Object subUnit;

    /**
     *  Form Fields
     */
    @JsonProperty("name")
    private String name;

    @JsonProperty("relation_name")
    private String relationName;

    @JsonProperty("phone_no")
    private String phoneNumber;

    @JsonProperty("designation")
    private Object designation;

    @JsonProperty("smartphone")
    private String smartphone;

    @JsonProperty("primary_member_id")
    private String primaryMemberID;

    @JsonProperty("age")
    private String age;

    @JsonProperty("mobiles")
    private List<Mobiles> mobiles;

   @JsonProperty("addresses")
   private List<Addresses> addresses;

    @JsonProperty("state")
    private String addressesState;

    @JsonProperty("whatsapp_no")
    private String whatsappNumber;

    @JsonProperty("std_code")
    private String stdCode;

    @JsonProperty("landline")
    private String landlineNumber;

    @JsonProperty("category")
    private String category;

    @JsonProperty("caste")
    private String caste;

    @JsonProperty("email")
    private String email;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("anniversary")
    private String anniversary;

    @JsonProperty("full_address")
    private String fullAddress;

    @JsonProperty("village")
    private String village;

    @JsonProperty("tehsil")
    private String tehsil;

    @JsonProperty("district")
    private String district;

    @JsonProperty("pin_code")
    private Integer pinCode;

    @JsonProperty("education")
    private String education;

    @JsonProperty("profession")
    private String profession;

    @JsonProperty("bike")
    private String bike;

    @JsonProperty("car")
    private String car;

    @JsonProperty("assembly_constituency")
    private int assemblyConstituency;

    @JsonProperty("booth")
    private String booth;

    @JsonProperty("voter_id")
    private String voterID;

    @JsonProperty("panna_number")
    private String pannaNumber;

    @JsonProperty("incharge_id")
    private String inChargeID;

    @JsonProperty("blood_group")
    private String bloodGroup;

    @JsonProperty("religion")
    private String religion;

    @JsonProperty("minister_status")
    private String ministerStatus;

    @JsonProperty("no_of_terms")
    private String noOfTerms;

    @JsonProperty("sub_caste")
    private String subCaste;

    @JsonProperty("salutation")
    private String salutation;

    @JsonProperty("delhi_address")
    private String delhiAddress;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("past_responsibility_party")
    private String pastResponsibilityParty;

    @JsonProperty("past_responsibility_gov")
    private String pastResponsibilityGov;

    @JsonProperty("current_responsibility")
    private String currentResponsibility;

    @JsonProperty("term_start")
    private String termStart;

    @JsonProperty("term_end")
    private String termEnd;

    @JsonProperty("voting_state")
    private String votingState;

    @JsonProperty("native_state")
    private String nativeState;

    @JsonProperty("pa_name")
    private String paName;

    @JsonProperty("pa_number")
    private String paNumber;

    @JsonProperty("facebook_profile")
    private String facebookProfile;

    @JsonProperty("twitter_profile")
    private String twitterProfile;

    @JsonProperty("instagram_profile")
    private String instagramProfile;

    @JsonProperty("linkedin_profile")
    private String linkedinProfile;







}
