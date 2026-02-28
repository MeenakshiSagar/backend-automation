package utils;

import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.auth.LoginRequest;
import models.auth.LoginResponse;
import models.auth.OtpSubmitRequest;
import models.auth.OtpSubmitResponse;
import services.AuthService;

public class RequestManager {

    /**
     * Returns a pre-configured RequestSpecification with base URI, content type, and logging filters.
     */
    public static RequestSpecification sendRequest() {
        return RestAssured.given()
                .baseUri(ConfigManager.getBaseUrl())
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());
    }

    /**
     * Performs a transient admin login using admin-config properties and returns a RequestSpecification
     * configured with the admin Authorization header. This does not modify global TokenStorage.
     */
    public static RequestSpecification adminRequest() {
        String adminPhone = ConfigManager.getAdminPhone();
        String adminOtp = ConfigManager.getAdminOtp();
        if (adminPhone == null || adminPhone.isEmpty()) {
            throw new RuntimeException("Admin phone not configured in admin-config.properties");
        }

        // Perform login and OTP submission to get admin auth token (transient)
        LoginRequest loginRequest = new LoginRequest(adminPhone);
        LoginResponse loginResponse = AuthService.login(loginRequest);

        OtpSubmitRequest otpReq = new OtpSubmitRequest();
        otpReq.setIdentificationToken(loginResponse.getIdentificationToken());
        otpReq.setOtp(adminOtp == null ? "" : adminOtp);
        OtpSubmitResponse otpResp = AuthService.submitOtp(otpReq);

        String adminToken = otpResp.getAuthToken();

        return sendRequest().header("Authorization", "Bearer " + adminToken);
    }
}
