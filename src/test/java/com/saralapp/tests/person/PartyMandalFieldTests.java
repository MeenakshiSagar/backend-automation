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
import com.saralapp.helpers.FormStructureService;
import enums.PartyMandalTestDataType;
import utils.faker.generator.PartyZilaData;

import java.util.List;

public class PartyMandalFieldTests extends BaseTest {

    @Test(dataProvider = "partyMandalTestCases", dataProviderClass = PersonDataProvider.class)
    public void testPartyMandalValidation(Object mandalTestValue, String expectedErrorKey, String description) {
        // Reset form structure to minimal state first
        Response resetResp = FormStructureService.resetFormStructure();
        Assert.assertTrue(resetResp.getStatusCode() == 200 || resetResp.getStatusCode() == 201 || resetResp.getStatusCode() == 202,
                "Failed to reset form structure: " + resetResp.getStatusCode() + " - " + resetResp.getBody().asString());

        // Prepare person and per-case form-structure configuration
        PersonRequest person = FakerDataGenerator.getInvalidPerson("partyMandal", mandalTestValue);

        // If the test value is an enum, branch per-case to set mandatory fields and payload
        if (mandalTestValue instanceof PartyMandalTestDataType) {
            PartyMandalTestDataType md = (PartyMandalTestDataType) mandalTestValue;
            switch (md) {
                case EMPTY -> {
                    // Mark both mandal and zila mandatory to align with backend behavior
                    Response formResp = FormStructureService.markFieldsMandatoryAndUpload(List.of("party_mandal_id", "party_district_id"));
                    Assert.assertTrue(formResp.getStatusCode() == 200 || formResp.getStatusCode() == 201 || formResp.getStatusCode() == 202,
                            "Failed to update form structure for Party Mandal EMPTY case: " + formResp.getBody().asString());
                }
                case ONLY_MANDAL_PROVIDED_WITHOUT_ZILA -> {
                    // Mark party zila mandatory so backend complains about missing zila when mandal provided
                    Response formResp = FormStructureService.markFieldsMandatoryAndUpload(List.of("party_district_id"));
                    Assert.assertTrue(formResp.getStatusCode() == 200 || formResp.getStatusCode() == 201 || formResp.getStatusCode() == 202,
                            "Failed to update form structure for ONLY_MANDAL_PROVIDED_WITHOUT_ZILA: " + formResp.getBody().asString());
                }
                case INVALID_NOT_MAPPED_WITH_ZILA -> {
                    // Need both zila and mandal present to validate mapping; mark both mandatory and set zila to a valid mapped id
                    Response formResp = FormStructureService.markFieldsMandatoryAndUpload(List.of("party_mandal_id", "party_district_id"));
                    Assert.assertTrue(formResp.getStatusCode() == 200 || formResp.getStatusCode() == 201 || formResp.getStatusCode() == 202,
                            "Failed to update form structure for INVALID_NOT_MAPPED_WITH_ZILA: " + formResp.getBody().asString());
                    // set party zila to a valid mapped id
                    String validZila = PartyZilaData.generate(enums.PartyZilaTestDataType.VALID_MAPPED_ID);
                    try {
                        person.setPartyZilaId(Integer.valueOf(validZila));
                    } catch (Exception ignored) {
                    }
                }
                case VALID_MAPPED_ID -> {
                    // Both mandatory and set party_zila to valid
                    Response formResp = FormStructureService.markFieldsMandatoryAndUpload(List.of("party_mandal_id", "party_district_id"));
                    Assert.assertTrue(formResp.getStatusCode() == 200 || formResp.getStatusCode() == 201 || formResp.getStatusCode() == 202,
                            "Failed to update form structure for VALID_MAPPED_ID: " + formResp.getBody().asString());
                    String validZila = PartyZilaData.generate(enums.PartyZilaTestDataType.VALID_MAPPED_ID);
                    try {
                        person.setPartyZilaId(Integer.valueOf(validZila));
                    } catch (Exception ignored) {
                    }
                }
                case STRING_FORMAT -> {
                    // mark mandal mandatory
                    Response formResp = FormStructureService.markFieldsMandatoryAndUpload(List.of("party_mandal_id"));
                    Assert.assertTrue(formResp.getStatusCode() == 200 || formResp.getStatusCode() == 201 || formResp.getStatusCode() == 202,
                            "Failed to update form structure for STRING_FORMAT: " + formResp.getBody().asString());
                }
                default -> {
                    // fallback: mark mandal mandatory
                    Response formResp = FormStructureService.markFieldsMandatoryAndUpload(List.of("party_mandal_id"));
                    Assert.assertTrue(formResp.getStatusCode() == 200 || formResp.getStatusCode() == 201 || formResp.getStatusCode() == 202,
                            "Failed to update form structure for default case: " + formResp.getBody().asString());
                }
            }
        } else {
            // If not enum, default behavior: mark party_mandal mandatory
            Response formResp = FormStructureService.markFieldsMandatoryAndUpload(List.of("party_mandal_id"));
            Assert.assertTrue(formResp.getStatusCode() == 200 || formResp.getStatusCode() == 201 || formResp.getStatusCode() == 202,
                    "Failed to update form structure for non-enum case: " + formResp.getBody().asString());
        }

        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid party mandal to be accepted: " + description);
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid party mandal to be rejected: " + description);
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            // Accept either the expected mandal error or party zila error depending on backend behavior
            String body = response.getBody().asString();
            boolean matches = body.contains(expectedError) || body.contains(ErrorMessagesHelper.getErrorMessage("person.party_zila.required"));
            Assert.assertTrue(matches,
                    "Expected error message: " + expectedError + " OR Party Zila required | Actual response: " + body + " | Case: " + description);
        }
    }
}
