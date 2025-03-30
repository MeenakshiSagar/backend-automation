package services;

import config.Endpoints;
import io.restassured.response.Response;
import models.person.PersonRequest;
import utils.AuthManager;
import utils.RequestManager;

public class PersonService {

    public static Response createPerson(PersonRequest person) {
        return RequestManager.sendRequest()
                .header("Authorization", "Bearer " + AuthManager.getValidToken())
                .body(person)
                .post(Endpoints.CREATE_PERSON);
    }

    public static Response getPerson(String personId) {
        return RequestManager.sendRequest()
                .header("Authorization", "Bearer " + AuthManager.getValidToken())
                .get(Endpoints.GET_PERSON.replace("{id}", personId));
    }
}
