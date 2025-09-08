package utils;

import com.github.javafaker.Faker;
import enums.*;
import models.person.PersonRequest;

import java.time.LocalDate;

public class

FakerDataGenerator {
    private static long phoneCounter = 6555555551L;

    private static final Faker faker = new Faker();

    //generate names for different combinations
    public static String generateName(NameTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case MIN_LENGTH -> faker.lorem().characters(1);
            case MAX_LENGTH -> faker.lorem().characters(51);
            case VALID -> faker.name().firstName();
            case INVALID_FORMAT -> faker.name().firstName() + faker.number().digits(2);
        };
    }

    public static String generatePhone(PhoneNumberTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case MIN_LENGTH -> faker.number().digits(7); // Less than 10 digits
            case MAX_LENGTH -> faker.number().digits(11); // More than 10 digits
            case STARTING_FROM_2_TO_4 -> {
                int firstDigit = 2 + faker.random().nextInt(3); // 2, 3, 4
                yield firstDigit + faker.number().digits(9);
            }
            case INVALID_FORMAT -> "123ABC7890"; // Mixed characters
            case INTEGER_FORMAT -> String.valueOf(8000000000L + faker.number().randomDigit());
            case STRING_FORMAT, VALID -> "9" + faker.number().digits(9); // Valid 10-digit phone
        };
    }

    public static String generateAge(AgeTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case LESS_THAN_MIN -> String.valueOf(Faker.instance().number().numberBetween(0, 17));
            case MORE_THAN_MAX -> String.valueOf(Faker.instance().number().numberBetween(124, 150));
            case EXACT_MIN -> "18";
            case EXACT_MAX -> "123";
            case VALID_RANGE -> String.valueOf(Faker.instance().number().numberBetween(19, 122));
            case STRING_NUMERIC -> "\"" + Faker.instance().number().numberBetween(18, 99) + "\""; // Numeric as string
            case STRING_NON_NUMERIC -> "eighteen";
            case SPECIAL_CHARACTERS -> "@ge#";
            case SPACES -> "  ";
            case DECIMAL_VALUE -> "25.5";
            case NEGATIVE -> "-25";
            case ZERO -> "0";
        };
    }

    public static String generatePinCode(PinCodeTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case LESS_THAN_6_DIGITS -> Faker.instance().number().digits(5);
            case MORE_THAN_6_DIGITS -> Faker.instance().number().digits(7);
            case VALID -> Faker.instance().number().digits(6);
            case STARTS_WITH_ZERO -> "0" + Faker.instance().number().digits(5);
            case ALL_ZEROS -> "000000";
            case STRING_FORMAT -> "\"" + Faker.instance().number().digits(6) + "\"";
            case INT_FORMAT -> String.valueOf(Integer.parseInt(Faker.instance().number().digits(6))); // parsed to int & back to string
        };
    }

    public static String generateDOB(DOBTestDataType type) {
        LocalDate today = LocalDate.now();
        return switch (type) {
            case EMPTY -> "";
            case VALID_FORMAT -> "1990-12-31";
            case INVALID_FORMAT -> "31-12-1990";
            case YEAR_AFTER_2007 -> "2010-01-01";
            case YEAR_BEFORE_1902 -> "1900-01-01";
            case ONLY_YEAR -> "1995";
            case ONLY_MONTH_AND_YEAR -> "1995-06";
            case INVALID_MONTH -> "1995-13-10";
            case INVALID_DAY -> "1995-12-32";
            case VALID_EDGE_CASE -> "1902-01-01";
            case FUTURE_DATE -> today.plusDays(1).toString();
            case NON_NUMERIC -> "abc";
            case DATE_WITH_TIME -> "1995-06-15T10:00:00";
            case SPACES_IN_DATE -> " 1995 - 06 - 15 ";
            case SPECIAL_CHARACTERS -> "1995@06#15";
        };
    }

    public static String generateAnniversary(AnniversaryTestDataType type) {
        LocalDate today = LocalDate.now();
        return switch (type) {
            case EMPTY -> "";
            case VALID_FORMAT -> "2010-05-20";
            case INVALID_FORMAT -> "20-05-2010";
            case FUTURE_DATE -> today.plusDays(1).toString();
            case ONLY_YEAR -> "2015";
            case ONLY_MONTH_AND_YEAR -> "2015-06";
            case INVALID_MONTH -> "2015-13-20";
            case INVALID_DAY -> "2015-12-32";
            case NON_NUMERIC -> "anniv";
            case DATE_WITH_TIME -> "2015-06-15T10:00:00";
            case SPACES_IN_DATE -> " 2015 - 06 - 15 ";
            case SPECIAL_CHARACTERS -> "2015@06#15";
            case ZEROS_IN_DATE -> "0000-00-00";
        };
    }

    public static String generateVoterNumber(VoterNumberTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case LESS_THAN_MIN_LENGTH -> faker.regexify("[A-Za-z0-9]{1,3}");
            case MORE_THAN_MAX_LENGTH -> faker.regexify("[A-Za-z0-9]{21,25}");
            case SPECIAL_CHARACTERS -> faker.regexify("[@#$%^&*!]{6,10}");
            case VALID_ALPHANUMERIC -> faker.regexify("[A-Za-z0-9]{10}");
            case ONLY_NUMERIC -> faker.number().digits(10);
            case ONLY_ALPHABETS -> faker.letterify("??????????"); // 10 alphabets
            case MIXED_CASE -> "AbCdEfGh12";
            case SPACES_INCLUDED -> "  AB12CD 34 ";
            case WITH_UNDERSCORE_OR_DASH -> "AB_12-CD34";
            case UNICODE_CHARACTERS -> "मतदाता१२३४"; // Hindi + digits (invalid)
            case ZERO_FILLED -> "0000000000";
            case VALID_EDGE_MIN_LENGTH -> faker.regexify("[A-Za-z0-9]{4}");
            case VALID_EDGE_MAX_LENGTH -> faker.regexify("[A-Za-z0-9]{20}");
        };
    }

    public static String generateAadhaarNumber(AadhaarNumberTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case LESS_THAN_12_DIGITS -> faker.number().digits(10);
            case MORE_THAN_12_DIGITS -> faker.number().digits(14);
            case EXACTLY_12_DIGITS -> faker.number().digits(12);
            case ALPHABETS_INCLUDED -> faker.bothify("##########AB");
            case SPECIAL_CHARACTERS -> "1234-5678-901@";
            case SPACES_INCLUDED -> "1234 5678 90";
            case LEADING_ZEROS -> "000012345678";
            case ONLY_SPECIAL_CHARACTERS -> "@#$%^&*!@#$";
            case NON_NUMERIC_STRING -> "TwelveDigits";
        };
    }

    public static String generateRationCardNumber(RationCardTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case LESS_THAN_MIN_LENGTH -> faker.lorem().characters(2);
            case MORE_THAN_MAX_LENGTH -> faker.lorem().characters(21);
            case BETWEEN_MIN_AND_MAX_LENGTH -> faker.lorem().characters(10);
            case EXACTLY_MIN_LENGTH -> faker.lorem().characters(3);
            case EXACTLY_MAX_LENGTH -> faker.lorem().characters(20);
            case INTEGER_FORMAT -> String.valueOf(faker.number().numberBetween(100000000, 999999999));
            case STRING_ALPHANUMERIC -> faker.bothify("??##??##??");
        };
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
//        person.setRelationName(faker.name().lastName());
//        person.setEmail(faker.internet().emailAddress());
        person.setPhoneNumber(String.valueOf(phoneCounter++)); // Increment phone number
//        person.setCategory("5");
        person.setAssemblyConstituency(365);
        return person;
    }


    public static PersonRequest getInvalidPerson(String field, String invalidValue) {
        PersonRequest person = getValidPerson(); // Start with a valid person

        switch (field) {
            case "name":
                person.setName(invalidValue);
                break;
            case "relationName":
                person.setRelationName(invalidValue);
                break;
            case "designation":
                person.setDesignation(invalidValue);
                break;
            case "smartphone":
                person.setSmartphone(invalidValue);
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
