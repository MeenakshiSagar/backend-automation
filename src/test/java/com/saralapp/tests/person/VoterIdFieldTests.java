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

public class VoterIdFieldTests extends BaseTest {

    @BeforeClass
    public void makeVoterIdMandatory() {
        Response resp = FormStructureService.markFieldsMandatoryAndUpload(java.util.List.of("voter_id"));
        if (!(resp.getStatusCode() == 200 || resp.getStatusCode() == 201 || resp.getStatusCode() == 202)) {
            throw new RuntimeException("Failed to mark voter_id field mandatory: " + resp.getStatusCode() + " - " + resp.getBody().asString());
        }
    }

    @Test(dataProvider = "voterIDTestCases", dataProviderClass = PersonDataProvider.class)
    public void testVoterIdValidation(Object voterTestValue, String expectedErrorKey, String description) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("voter_id", voterTestValue);
        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Valid voter id value is expected: " + description);
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid voter id value is rejected: " + description);
            String lookupKey = expectedErrorKey.startsWith("person.") ? expectedErrorKey : "person." + expectedErrorKey;
            String expectedErrorMessage = ErrorMessagesHelper.getErrorMessage(lookupKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedErrorMessage),
                    "Expected error message: " + expectedErrorMessage + " | Case: " + description);

        }
    }
}
