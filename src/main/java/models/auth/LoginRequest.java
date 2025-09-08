package models.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON payload
public class LoginRequest {

    // Getters & Setters (Optional, if needed)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("web_login")
    private boolean webLogin;

    // ✅ Constructor for phone login (No password)
    public LoginRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.webLogin = true;  // Assuming web login is always true
    }

    // ✅ Constructor for email login (Requires password)
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.webLogin = true;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWebLogin(boolean webLogin) {
        this.webLogin = webLogin;
    }
    public void getSomething(){
        System.out.println("Something went wrong");
    }
}
