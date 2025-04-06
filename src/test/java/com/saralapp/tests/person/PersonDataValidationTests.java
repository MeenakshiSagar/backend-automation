package com.saralapp.tests.person;

import com.saralapp.helpers.ErrorMessagesHelper;
import com.saralapp.tests.BaseTest;
import io.restassured.response.Response;
import models.person.PersonRequest;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.PersonService;
import utils.FakerDataGenerator;
import com.saralapp.testdata.PersonDataProvider;

public class PersonDataValidationTests extends BaseTest {

    @Test(dataProvider = "nameTestCases", dataProviderClass = PersonDataProvider.class)
    public void testNameFieldValidation(String testName, String expectedErrorKey) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("name", testName);
        Response response = PersonService.createPerson(person);
        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid payload to be accepted.");
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected payload to be rejected.");
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError);
        }
    }
}
