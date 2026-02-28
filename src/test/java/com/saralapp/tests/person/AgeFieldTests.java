package com.saralapp.tests.person;

import com.saralapp.helpers.ErrorMessagesHelper;
import com.saralapp.tests.BaseTest;
import io.restassured.response.Response;
import models.person.PersonRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.PersonService;
import utils.FakerDataGenerator;
import com.saralapp.testdata.PersonDataProvider;
import com.saralapp.helpers.FormStructureService;

public class AgeFieldTests extends BaseTest {

    @BeforeClass
    public void makeAgeMandatory() {
        // Ensure form reset was done in @BeforeSuite; now mark only age mandatory for this test class
        Response resp = FormStructureService.markFieldsMandatoryAndUpload(java.util.List.of("age"));
        if (!(resp.getStatusCode() == 200 || resp.getStatusCode() == 201 || resp.getStatusCode() == 202)) {
            throw new RuntimeException("Failed to mark age field mandatory: " + resp.getStatusCode() + " - " + resp.getBody().asString());
        }
    }

    @Test(dataProvider = "ageTestCases", dataProviderClass = PersonDataProvider.class)
    public void testAgeValidation(String ageTestValue, String expectedErrorKey, String description) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("age", ageTestValue);
        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Valid age value is expected: " + description);
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid age value is rejected: " + description);
            String expectedErrorMessage = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedErrorMessage),
                    "Expected error message: " + expectedErrorMessage + " | Case: " + description);
        }
    }
}
