package com.saralapp.tests.auth;

import com.saralapp.testdata.AuthDataProvider;
import org.testng.annotations.Test;
import utils.AuthManager;
import utils.TokenStorage;

import static org.testng.Assert.*;


public class AuthAPITests {

    @Test
    public void testPhoneLoginFlow() {
        TokenStorage storage = TokenStorage.getInstance();
        assertNotNull(storage.getAuthToken());
        assertNotNull(storage.getRefreshToken());
    }

    @Test(dataProvider = "loginMethods", dataProviderClass = AuthDataProvider.class)
    public void testDifferentLoginMethods(boolean usePhone) {
        AuthManager.initializeSession(usePhone);
        assertFalse(TokenStorage.getInstance().isTokenExpired());
    }
//
//    @Test
//    public void testTokenRefresh() {
//        String originalToken = TokenStorage.getInstance().getAuthToken();
//        // Force expiration
//        TokenStorage.getInstance().storeTokens(originalToken,
//                TokenStorage.getInstance().getRefreshToken(), 0);
//
//        String newToken = AuthManager.getValidToken();
//        assertNotEquals(originalToken, newToken);
//   }
}
