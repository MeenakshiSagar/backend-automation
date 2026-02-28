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

public class RelationNameFieldTests extends BaseTest {

    @BeforeClass
    public void makeRelationNameMandatory() {
        Response resp = FormStructureService.markFieldsMandatoryAndUpload(java.util.List.of("relation_name"));
        if (!(resp.getStatusCode() == 200 || resp.getStatusCode() == 201 || resp.getStatusCode() == 202)) {
            throw new RuntimeException("Failed to mark relation_name field mandatory: " + resp.getStatusCode() + " - " + resp.getBody().asString());
        }
    }

    @Test(dataProvider = "relationNameTestCases", dataProviderClass = PersonDataProvider.class)
    public void testRelationFieldValidation(String testName, String expectedErrorKey, String description) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("relationName", testName);
        Response response = PersonService.createPerson(person);
        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid payload to be accepted: " + description);
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected payload to be rejected: " + description);
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError + " | Case: " + description);
        }
    }
}
