package models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;  // ✅ Handles "message": "OTP sent successfully"

    @JsonProperty("identification_token")
    private String identificationToken;

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdentificationToken() {
        return identificationToken;
    }

    public void setIdentificationToken(String identificationToken) {
        this.identificationToken = identificationToken;
    }
}
