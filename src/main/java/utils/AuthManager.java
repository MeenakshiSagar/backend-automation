package utils;

import config.ConfigManager;
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
     * Initialize session using explicit phone number (useful for admin actions).
     * @param phone the phone number to login as (e.g., "6000000123")
     */
    public static void initializeSessionWithPhone(String phone) {
        try {
            LoginRequest loginRequest = new LoginRequest(phone);
            LoginResponse loginResponse = AuthService.login(loginRequest);

            OtpSubmitRequest otpRequest = new OtpSubmitRequest();
            otpRequest.setIdentificationToken(loginResponse.getIdentificationToken());
            otpRequest.setOtp(ConfigManager.getTestOtp());

            OtpSubmitResponse otpResponse = AuthService.submitOtp(otpRequest);
            TokenStorage.getInstance().storeTokens(otpResponse.getAuthToken(), otpResponse.getRefreshToken());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize session for phone " + phone + ": " + e.getMessage(), e);
        }
    }

    /**
     * Returns a valid auth token. If the token is expired, attempts to refresh it using the refresh token.
     * If refresh fails (for example refresh token expired), falls back to full re-login.
     * @return a valid auth token.
     */
    public static String getValidToken() {
        TokenStorage tokenStorage = TokenStorage.getInstance();

        if (tokenStorage.isTokenExpired()) {
            // Try refresh first if we have a refresh token
            String refreshTok = tokenStorage.getRefreshToken();
            if (refreshTok != null && !refreshTok.isEmpty()) {
                try {
                    RefreshTokenRequest refreshRequest = new RefreshTokenRequest();
                    refreshRequest.setRefreshToken(refreshTok);

                    RefreshTokenResponse refreshResponse = AuthService.refreshToken(refreshRequest);

                    if (refreshResponse != null && refreshResponse.getAuthToken() != null) {
                        tokenStorage.storeTokens(refreshResponse.getAuthToken(), refreshResponse.getRefreshToken());
                        return tokenStorage.getAuthToken();
                    } else {
                        System.out.println("Refresh token call did not return new tokens; falling back to full login.");
                    }
                } catch (RuntimeException re) {
                    // Common case: refresh token expired or invalid
                    System.out.println("Refresh token failed: " + re.getMessage() + "; attempting full re-login.");
                    // fall through to re-login
                } catch (Exception e) {
                    System.out.println("Unexpected error while refreshing token: " + e.getMessage() + "; attempting full re-login.");
                }
            } else {
                System.out.println("No refresh token available; performing full login.");
            }

            // As refresh failed or wasn't available, attempt full re-login once
            try {
                // Use phone login by default to match existing tests
                initializeSession(true);
                if (TokenStorage.getInstance().getAuthToken() == null) {
                    throw new RuntimeException("Re-login did not produce an auth token.");
                }
                return TokenStorage.getInstance().getAuthToken();
            } catch (Exception ex) {
                throw new RuntimeException("Failed to refresh or re-login: " + ex.getMessage(), ex);
            }
        }
        return tokenStorage.getAuthToken();
    }
}
