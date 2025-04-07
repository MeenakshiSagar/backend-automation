package utils;

import com.github.javafaker.Faker;
import enums.TestCaseType;
import models.person.PersonRequest;


public class FakerDataGenerator {
    private static long phoneCounter = 8000111190L;

    private static final Faker faker = new Faker();

    //generate names for different combinations
    public static String generateName(TestCaseType type) {
        return switch (type) {
            case EMPTY -> "";
            case MIN_LENGTH -> faker.lorem().characters(1);
            case MAX_LENGTH -> faker.lorem().characters(51);
            case VALID -> faker.name().firstName();
            case INVALID_FORMAT -> faker.name().firstName() + faker.number().digits(2);
        };
    }

    // Generates a valid email
    public static String getValidEmail() {
        return faker.internet().emailAddress();
    }

    // Generates an invalid email
    public static String getInvalidEmail() {
        return faker.lorem().characters(10) + "@invalid";  // Missing domain
    }

    // Generates a valid phone number (10-digit)
    public static String getValidPhoneNumber() {
        return faker.phoneNumber().subscriberNumber(10);
    }

    // Generates an invalid phone number (less than 10 digits)
    public static String getShortPhoneNumber() {
        return faker.number().digits(8);
    }

    // Generates an invalid phone number (more than 10 digits)
    public static String getLongPhoneNumber() {
        return faker.number().digits(12);
    }

    // Generates a valid designation
    public static String getValidDesignation() {
        return faker.job().title();
    }

    // Generates a valid level name
    public static String getValidLevelName() {
        return faker.company().industry();
    }

    // Generates a valid data type
    public static String getValidDataType() {
        return faker.options().option("Abhiyan", "Karyakarta", "Primary Member");
    }

    // Generates a valid unit
    public static String getValidUnit() {
        return faker.address().state();
    }

    // Generates a valid sub-unit
    public static String getValidSubUnit() {
        return faker.address().city();
    }

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
        person.setName(faker.name().firstName());
        person.setRelationName(faker.name().lastName());
        person.setEmail(faker.internet().emailAddress());
        person.setPhoneNumber(String.valueOf(phoneCounter++)); // Increment phone number
        person.setCategory("5");
        person.setAssemblyConstituency(363);
        return person;
    }

    public static PersonRequest getInvalidPerson(String field, String invalidValue) {
        PersonRequest person = getValidPerson(); // Start with a valid person

        switch (field) {
            case "name":
                person.setName(invalidValue);
                break;
            case "email":
                person.setEmail(invalidValue);
                break;
            case "phoneNumber":
                person.setPhoneNumber(invalidValue);
                break;
        }
        return person;
    }
}
