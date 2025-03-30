package models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpSubmitRequest {

    @JsonProperty("identification_token")  // ✅ Updated key name
    private String identificationToken;

    @JsonProperty("otp")
    private String otp;

    // Getters and Setters
    public String getIdentificationToken() {
        return identificationToken;
    }

    public void setIdentificationToken(String identificationToken) {
        this.identificationToken = identificationToken;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
