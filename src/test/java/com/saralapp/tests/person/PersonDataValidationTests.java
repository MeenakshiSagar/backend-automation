package com.saralapp.tests.person;

import com.saralapp.helpers.ErrorMessagesHelper;
import com.saralapp.tests.BaseTest;
import io.restassured.response.Response;
import models.person.PersonRequest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.PersonService;
import utils.FakerDataGenerator;

import static com.saralapp.helpers.StringHelper.repeat;

public class PersonDataValidationTests extends BaseTest {

    // ------------------ Name Field Test Cases ------------------
    @DataProvider(name = "nameTestCases")
    public Object[][] nameTestCases() {
        return new Object[][]{
                {"", "person.person_name.required"}, // Empty name
                {"J", "person.person_name.minLength"}, // Single character
                {repeat("A", 51), "person.person_name.maxLength"}, // More than 50 characters
                {"John_Doe!", "person.person_name.invalidFormat"}, // Invalid characters
                {"John", null}  // Valid name
        };
    }

    @Test(dataProvider = "nameTestCases")
    public void testNameFieldValidation(String testName, String expectedErrorKey) {
        PersonRequest person = FakerDataGenerator.getValidPerson();
        person.setName(testName);

        Response response = PersonService.createPerson(person);
        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid payload to be accepted.");
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected payload to be rejected.");
            String expectedError = ErrorMessagesHelper.getErrorMessage("person.person_name.minLength");
            Assert.assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError);
        }
    }
}
