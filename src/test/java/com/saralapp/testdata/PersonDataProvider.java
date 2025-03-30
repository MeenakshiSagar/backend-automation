package com.saralapp.testdata;

import models.person.PersonRequest;
import org.testng.annotations.DataProvider;

public class PersonDataProvider {

    public static PersonRequest getValidPerson() {
        PersonRequest person = new PersonRequest();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@example.com");
        person.setPhone("1234567890");
        return person;
    }

    @DataProvider(name = "validPersons")
    public static Object[][] validPersons() {
        return new Object[][]{
                {getValidPerson()}
        };
    }

//    // Optional: Data provider for invalid persons
//    @DataProvider(name = "invalidPersons")
//    public static Object[][] invalidPersons() {
//        PersonRequest invalidPerson = new PersonRequest();
//        // Missing or invalid fields for testing purposes
//        invalidPerson.setFirstName(\"\"); // empty first name
//                invalidPerson.setLastName(\"\");  // empty last name
//                        invalidPerson.setEmail(\"invalid-email\"); // invalid email format
//                                invalidPerson.setPhone(\"\");     // empty phone
//
//        return new Object[][] {
//                { invalidPerson }
//        };
//    }
}
