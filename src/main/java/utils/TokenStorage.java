package utils;

import java.time.Instant;
import java.util.Base64;

import org.json.JSONObject;

public class TokenStorage {
    private static TokenStorage instance;
    private String authToken;
    private String refreshToken;
    private Instant expiresAt;

    private TokenStorage() {
    }

    public static synchronized TokenStorage getInstance() {
        if (instance == null) {
            instance = new TokenStorage();
        }
        return instance;
    }

    /**
     * Stores tokens and calculates the expiration time by decoding the JWT.
     */
    public void storeTokens(String authToken, String refreshToken) {
        this.authToken = authToken;
        this.refreshToken = refreshToken;
        this.expiresAt = parseExpiryFromJWT(authToken);
    }


    /**
     * Parses the JWT token to extract the "exp" claim as an Instant.
     */
    private Instant parseExpiryFromJWT(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid JWT token");
            }
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JSONObject json = new JSONObject(payload);
            long exp = json.getLong("exp"); // seconds since epoch
            return Instant.ofEpochSecond(exp);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JWT token expiration", e);
        }
    }

    public boolean isTokenExpired() {
        return expiresAt == null || Instant.now().isAfter(expiresAt);
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }


}
