package com.saralapp.tests.person;

import com.saralapp.helpers.ErrorMessagesHelper;
import com.saralapp.tests.BaseTest;
import enums.PhoneNumberTestDataType;
import io.restassured.response.Response;
import models.person.PersonRequest;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.PersonService;
import utils.FakerDataGenerator;
import com.saralapp.testdata.PersonDataProvider;
import static org.testng.Assert.assertTrue;

public class PersonDataValidationTests extends BaseTest {

    /**
     * these test will run all the test cases which validate name field.
     */
    @Test(dataProvider = "nameTestCases", dataProviderClass = PersonDataProvider.class)
    public void testNameFieldValidation(String testName, String expectedErrorKey) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("name", testName);
        Response response = PersonService.createPerson(person);
        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid payload to be accepted.");
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected payload to be rejected.");
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError);
        }
    }

    /**
     * these test will run all the test cases which validate relation name field.
     */
    @Test(dataProvider = "relationNameTestCases", dataProviderClass = PersonDataProvider.class)
    public void testRelationFieldValidation(String testName, String expectedErrorKey) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("relationName", testName);
        Response response = PersonService.createPerson(person);
        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid payload to be accepted.");
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected payload to be rejected.");
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError);
        }
    }

    @Test(dataProvider = "phoneNumberTestCases", dataProviderClass = PersonDataProvider.class)
    public void testPhoneNumberValidation(String phoneTestName, String expectedErrorKey) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("phoneNumber", phoneTestName);

        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid phone number to be accepted.");
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid phone number to be rejected.");
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError +
                            " | Actual response: " + response.getBody().asString());
        }
    }

    @Test(dataProvider = "rationCardTestCases", dataProviderClass = PersonDataProvider.class)
    public void testRationCardValidation(String testName, String expectedErrorKey) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("rationCard", testName);
        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid Ration Card Number to be accepted.");
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid Ration Card Number to be rejected.");
            String expectedErrorMessage = ErrorMessagesHelper.getErrorMessage("person." + expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedErrorMessage),
                    "Expected error message: " + expectedErrorMessage);
        }
    }

    @Test(dataProvider = "ageTestCases", dataProviderClass = PersonDataProvider.class)
    public void testAgeValidation(String testName, String expectedErrorKey){

        PersonRequest person = FakerDataGenerator.getInvalidPerson("age", testName);
        Response response = PersonService.createPerson(person);
        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Valid age value is expected.");
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid age value is rejected.");
            String expectedErrorMessage = ErrorMessagesHelper.getErrorMessage("person." + expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedErrorMessage),
                    "Expected error message: " + expectedErrorMessage);
        }

    }
}
