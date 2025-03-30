package com.saralapp.testdata;

import org.testng.annotations.DataProvider;

public class AuthDataProvider {

    @DataProvider(name = "loginMethods")
    public static Object[][] loginMethods() {
        return new Object[][] {
                {true},  // Phone login
                {false}  // Email login
        };
    }
}
