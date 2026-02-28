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

public class PhoneNumberFieldTests extends BaseTest {

    @BeforeClass
    public void makePhoneMandatory() {
        Response resp = FormStructureService.markFieldsMandatoryAndUpload(java.util.List.of("phone_no"));
        if (!(resp.getStatusCode() == 200 || resp.getStatusCode() == 201 || resp.getStatusCode() == 202)) {
            throw new RuntimeException("Failed to mark phone_no field mandatory: " + resp.getStatusCode() + " - " + resp.getBody().asString());
        }
    }

    @Test(dataProvider = "phoneNumberTestCases", dataProviderClass = PersonDataProvider.class)
    public void testPhoneNumberValidation(Object phoneTestValue, String expectedErrorKey, String description) {
        PersonRequest person = FakerDataGenerator.getInvalidPerson("phoneNumber", phoneTestValue);

        Response response = PersonService.createPerson(person);

        if (expectedErrorKey == null) {
            Assert.assertEquals(response.getStatusCode(), 201, "Expected valid phone number to be accepted: " + description);
        } else {
            Assert.assertEquals(response.getStatusCode(), 400, "Expected invalid phone number to be rejected: " + description);
            String expectedError = ErrorMessagesHelper.getErrorMessage(expectedErrorKey);
            Assert.assertTrue(response.getBody().asString().contains(expectedError),
                    "Expected error message: " + expectedError + " | Actual response: " + response.getBody().asString() + " | Case: " + description);
        }
    }
}
