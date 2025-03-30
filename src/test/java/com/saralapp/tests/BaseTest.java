package com.saralapp.tests;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import utils.AuthManager;
import utils.RequestManager;

public class BaseTest {

    protected RequestSpecification request;

    @BeforeMethod
    public void setup() {
        AuthManager.initializeSession(true);
        request = RequestManager.sendRequest()
                .header("Authorization", "Bearer " + AuthManager.getValidToken());
    }
}
