package com.saralapp.tests;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.AuthManager;
import utils.RequestManager;
import com.saralapp.helpers.FormStructureService;
import io.restassured.response.Response;

public class BaseTest {

    protected RequestSpecification request;

    @BeforeSuite
    public void resetFormStructureBeforeSuite() {
        // Reset form structure once before the entire test suite so tests can configure per-field mandatory flags
        Response resp = FormStructureService.resetFormStructure();
        if (resp == null || !(resp.getStatusCode() == 200 || resp.getStatusCode() == 201 || resp.getStatusCode() == 202)) {
            throw new RuntimeException("Failed to reset form structure before suite. Response: " + (resp == null ? "null" : resp.getStatusCode() + " - " + resp.getBody().asString()));
        }
    }

    @BeforeMethod
    public void setup() {
        AuthManager.initializeSession(true);
        request = RequestManager.sendRequest()
                .header("Authorization", "Bearer " + AuthManager.getValidToken());
    }
}
