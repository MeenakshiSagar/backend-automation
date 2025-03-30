package services;

import config.Endpoints;
import exceptions.AuthException;
import io.restassured.response.Response;
import models.auth.*;
import utils.RequestManager;
import utils.TokenStorage;

public class AuthService {

    public static LoginResponse login(LoginRequest request) {
        Response response = RequestManager.sendRequest()
                .body(request)
                .post(Endpoints.LOGIN);

        if (response.getStatusCode() == 200) {
            return response.as(LoginResponse.class);
        } else {
            throw new RuntimeException("Login failed: " + response.getBody().asString());
        }
    }

    public static OtpSubmitResponse submitOtp(OtpSubmitRequest request) {
        Response response = RequestManager.sendRequest()
                .body(request)
                .post(Endpoints.SUBMIT_OTP);

        if (response.getStatusCode() != 200) {
            throw new AuthException("OTP submission failed: " + response.getBody().asString());
        }

        // Parse the response for tokens
        OtpSubmitResponse otpResponse = response.as(OtpSubmitResponse.class);

        // Store tokens (Expiration is parsed from JWT token)
        TokenStorage.getInstance().storeTokens(
                otpResponse.getAuthToken(),
                otpResponse.getRefreshToken()
        );

        return otpResponse;
    }

    public static RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        Response response = RequestManager.sendRequest()
                .body(request)
                .post(Endpoints.REFRESH_TOKEN);

        if (response.getStatusCode() == 200) {
            return response.as(RefreshTokenResponse.class);  // ✅ Convert JSON to RefreshTokenResponse
        } else {
            throw new RuntimeException("Failed to refresh token: " + response.getBody().asString());
        }
    }
}
