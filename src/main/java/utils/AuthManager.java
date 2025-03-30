package utils;

import config.ConfigManager;
import exceptions.AuthException;
import models.auth.*;
import services.AuthService;

public class AuthManager {


    /**
     * Initializes a session by performing login and OTP submission.
     * @param usePhoneLogin if true, uses phone login; otherwise, uses email login.
     */
    public static void initializeSession(boolean usePhoneLogin) {

        LoginRequest loginRequest;

        if (usePhoneLogin) {
            loginRequest = new LoginRequest(ConfigManager.getPhone()); // ✅ Phone login: No password
        } else {
            loginRequest = new LoginRequest(ConfigManager.getEmail(), ConfigManager.getPassword()); // ✅ Email login: Requires password
        }

        // Call login API
        LoginResponse loginResponse = AuthService.login(loginRequest);

        // Handle OTP Submission
        OtpSubmitRequest otpRequest = new OtpSubmitRequest();
        otpRequest.setIdentificationToken(loginResponse.getIdentificationToken());
        otpRequest.setOtp(ConfigManager.getTestOtp());

        OtpSubmitResponse otpResponse = AuthService.submitOtp(otpRequest);

        // Store tokens
        TokenStorage.getInstance().storeTokens(otpResponse.getAuthToken(), otpResponse.getRefreshToken());

    }

    /**
     * Returns a valid auth token. If the token is expired, refreshes it.
     * @return a valid auth token.
     */
    public static String getValidToken() {
        TokenStorage tokenStorage = TokenStorage.getInstance();

        if (tokenStorage.isTokenExpired()) {
            RefreshTokenRequest refreshRequest = new RefreshTokenRequest();
            refreshRequest.setRefreshToken(tokenStorage.getRefreshToken());

            RefreshTokenResponse refreshResponse = AuthService.refreshToken(refreshRequest);

            if (refreshResponse != null && refreshResponse.getAuthToken() != null) {
                tokenStorage.storeTokens(refreshResponse.getAuthToken(), refreshResponse.getRefreshToken());
            } else {
                throw new RuntimeException("Failed to obtain new auth token.");
            }
        }
        return tokenStorage.getAuthToken();
    }
}
