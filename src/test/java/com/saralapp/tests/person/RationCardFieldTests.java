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

public class RationCardFieldTests extends BaseTest {

    @BeforeClass
    public void makeRationCardFieldsMandatory() {
        Response resp = FormStructureService.markFieldsMandatoryAndUpload(java.util.List.of("ration_card_number", "ration_card_url"));
        if (!(resp.getStatusCode() == 200 || resp.getStatusCode() == 201 || resp.getStatusCode() == 202)) {
            throw new RuntimeException("Failed to mark ration card fields mandatory: " + resp.getStatusCode() + " - " + resp.getBody().asString());
        }
    }

    @Test(dataProvider = "rationCardTestCases", dataProviderClass = PersonDataProvider.class)
    public void testRationCardValidation(String testName, String expectedErrorKey, String description) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("ration_card_number", testName);
        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid Ration Card Number to be accepted: " + description);
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid Ration Card Number to be rejected: " + description);
            String expectedErrorMessage = ErrorMessagesHelper.getErrorMessage("person." + expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedErrorMessage),
                    "Expected error message: " + expectedErrorMessage + " | Case: " + description);
        }
    }
}
