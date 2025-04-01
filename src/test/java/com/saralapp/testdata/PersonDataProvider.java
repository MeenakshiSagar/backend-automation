package com.saralapp.testdata;

import models.person.PersonRequest;
import org.testng.annotations.DataProvider;
import utils.FakerDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class PersonDataProvider {

    public static PersonRequest getValidPerson() {
        PersonRequest person = new PersonRequest();
        person.setName(FakerDataGenerator.getValidName());
        person.setRelationName(FakerDataGenerator.getValidName());
        person.setPhoneNumber("8989891111");
        return person;
    }

    @DataProvider(name = "validPersons")
    public static Object[][] validPersons() {
        return new Object[][]{
                {getValidPerson()}
        };
    }


    @DataProvider(name = "fieldSpecificTests")
    public static Object[][] fieldSpecificTests() {
        List<Object[]> testCases = new ArrayList<>();

        // Add test cases for different invalid field scenarios
        testCases.add(new Object[]{FakerDataGenerator.getInvalidPerson("firstName", ""), "person.firstName.required"});
        testCases.add(new Object[]{FakerDataGenerator.getInvalidPerson("firstName", "J"), "person.firstName.minLength"});
        testCases.add(new Object[]{FakerDataGenerator.getInvalidPerson("email", "invalid-email"), "person.email.invalid"});
        testCases.add(new Object[]{FakerDataGenerator.getInvalidPerson("phoneNumber", "123"), "person.phoneNumber.invalid"});

        return testCases.toArray(new Object[0][2]);
    }

}
