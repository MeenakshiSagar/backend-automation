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

public class FullAddressFieldTests extends BaseTest {

    @BeforeClass
    public void makeFullAddressMandatory() {
        Response resp = FormStructureService.markFieldsMandatoryAndUpload(java.util.List.of("full_address"));
        if (!(resp.getStatusCode() == 200 || resp.getStatusCode() == 201 || resp.getStatusCode() == 202)) {
            throw new RuntimeException("Failed to mark full_address field mandatory: " + resp.getStatusCode() + " - " + resp.getBody().asString());
        }
    }

    @Test(dataProvider = "fullAddressTestCases", dataProviderClass = PersonDataProvider.class)
    public void testFullAddressValidation(Object fullAddressValue, String expectedErrorKey, String description) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("full_address", fullAddressValue);
        String payload = JsonUtils.toJson(person);
        Reporter.log("Payload for FullAddress test (" + description + "): " + payload, true);

        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid full_address to be accepted: " + description);
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid full_address to be rejected: " + description);
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError + " | Actual: " + response.getBody().asString() + " | Case: " + description);
        }
    }
}
