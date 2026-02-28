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
import utils.JsonUtils;
import org.testng.Reporter;
import com.saralapp.helpers.FormStructureService;

import java.util.List;

public class PartyZilaFieldTests extends BaseTest {

    @Test(dataProvider = "partyZilaTestCases", dataProviderClass = PersonDataProvider.class)
    public void testPartyZilaValidation(Object zilaTestValue, String expectedErrorKey, String description) {
        // Reset form structure to minimal state first
        Response resetResp = FormStructureService.resetFormStructure();
        Assert.assertTrue(resetResp.getStatusCode() == 200 || resetResp.getStatusCode() == 201 || resetResp.getStatusCode() == 202,
                "Failed to reset form structure: " + resetResp.getStatusCode() + " - " + resetResp.getBody().asString());

        // Then mark only party_district_id mandatory for this test
        Response formResp = FormStructureService.markFieldsMandatoryAndUpload(List.of("party_district_id"));
        Assert.assertTrue(formResp.getStatusCode() == 200 || formResp.getStatusCode() == 201 || formResp.getStatusCode() == 202,
                "Failed to update form structure for Party Zila test: " + formResp.getBody().asString());

        PersonRequest person = FakerDataGenerator.getInvalidPerson("partyZila", zilaTestValue);

        // Log payload for debugging
        String payloadJson = JsonUtils.toJson(person);
        System.out.println("Person payload for PartyZila test (" + description + "):\n" + payloadJson);
        Reporter.log("Person payload for PartyZila test (" + description + "):\n" + payloadJson, true);

        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid party zila to be accepted: " + description);
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid party zila to be rejected: " + description);
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError + " | Actual response: " + response.getBody().asString() + " | Case: " + description);
        }
    }
}
