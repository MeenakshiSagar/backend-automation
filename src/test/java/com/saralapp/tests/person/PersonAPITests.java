package com.saralapp.tests.person;

import com.saralapp.testdata.PersonDataProvider;
import com.saralapp.tests.BaseTest;
import config.Endpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.person.PersonRequest;
import models.person.PersonResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.PersonService;

public class PersonAPITests extends BaseTest {

    @Test(dataProvider = "validPersons", dataProviderClass = PersonDataProvider.class)
    public void testCreateValidPerson(PersonRequest testPerson) {
        Response response = PersonService.createPerson(testPerson);

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(response.as(PersonResponse.class).getId());
    }

    @Test
    public void testUnauthorizedAccess() {
        Response response = RestAssured.given()
                .body(PersonDataProvider.getValidPerson())
                .post(Endpoints.CREATE_PERSON);

        Assert.assertEquals(response.getStatusCode(), 401);
    }
}
