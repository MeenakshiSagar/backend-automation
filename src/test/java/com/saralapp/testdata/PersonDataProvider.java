package com.saralapp.testdata;

import models.person.PersonRequest;
import org.testng.annotations.DataProvider;
import utils.FakerDataGenerator;
import utils.FormFilterData;

import java.util.ArrayList;
import java.util.List;

public class PersonDataProvider {

    public static PersonRequest getValidPerson() {
        PersonRequest person = new PersonRequest();

        // Mandatory Form Fields (Taken from a Constants File)
        person.setLevelName(FormFilterData.LEVEL_NAME);
        person.setLevel(FormFilterData.LEVEL);
        person.setDataType(FormFilterData.DATA_TYPE);
        person.setDataUnit(FormFilterData.DATA_UNIT);
        person.setSubUnit(FormFilterData.SUB_UNIT);
        person.setDesignation(FormFilterData.DESIGNATION);

        //Form Fields
        person.setName(FakerDataGenerator.getValidName());
        person.setPhoneNumber(FakerDataGenerator.getValidPhoneNumber());
        person.setCategory("2");
        person.setAssemblyConstituency("363");
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
