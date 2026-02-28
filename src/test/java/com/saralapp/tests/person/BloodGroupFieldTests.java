package com.saralapp.tests.person;

import com.saralapp.helpers.ErrorMessagesHelper;
import com.saralapp.tests.BaseTest;
import io.restassured.response.Response;
import models.person.PersonRequest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.PersonService;
import utils.FakerDataGenerator;
import com.saralapp.testdata.PersonDataProvider;
import com.saralapp.helpers.FormStructureService;
import utils.JsonUtils;

public class BloodGroupFieldTests extends BaseTest {

    @BeforeClass
    public void makeBloodGroupMandatory() {
        Response resp = FormStructureService.markFieldsMandatoryAndUpload(java.util.List.of("blood_group"));
        if (!(resp.getStatusCode() == 200 || resp.getStatusCode() == 201 || resp.getStatusCode() == 202)) {
            throw new RuntimeException("Failed to mark blood_group field mandatory: " + resp.getStatusCode() + " - " + resp.getBody().asString());
        }
    }

    @Test(dataProvider = "bloodGroupTestCases", dataProviderClass = PersonDataProvider.class)
    public void testBloodGroupValidation(Object bgValue, String expectedErrorKey, String description) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("blood_group", bgValue);
        String payload = JsonUtils.toJson(person);
        Reporter.log("Payload for BloodGroup test (" + description + "): " + payload, true);

        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid blood group to be accepted: " + description);
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid blood group to be rejected: " + description);
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError + " | Actual: " + response.getBody().asString() + " | Case: " + description);
        }
    }
}

