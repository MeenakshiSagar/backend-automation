package com.saralapp.testdata;

import enums.*;
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
        person.setName(FakerDataGenerator.generateName(NameTestDataType.VALID));
        person.setPhoneNumber(FakerDataGenerator.generatePhone(PhoneNumberTestDataType.VALID));
        person.setCategory("2");
        person.setAssemblyConstituency(363);
        return person;
    }

    @DataProvider(name = "validPersons")
    public static Object[][] validPersons() {
        return new Object[][]{
                {getValidPerson()}
        };
    }

    // ------------------ Name Field Test Cases ------------------
    @DataProvider(name = "nameTestCases")
    public Object[][] nameTestCases() {
        return new Object[][]{
                {FakerDataGenerator.generateName(NameTestDataType.EMPTY), "person.person_name.required"}, // Empty name
                {FakerDataGenerator.generateName(NameTestDataType.MIN_LENGTH), "person.person_name.minLength"}, // Single character
                {FakerDataGenerator.generateName(NameTestDataType.MAX_LENGTH), "person.person_name.maxLength"}, // More than 50 characters
                {FakerDataGenerator.generateName(NameTestDataType.INVALID_FORMAT), "person.person_name.invalidFormat"}, // Invalid characters
                {FakerDataGenerator.generateName(NameTestDataType.VALID), null}  // Valid name
        };
    }

    // ------------------ Relation Field Test Cases ------------------
    @DataProvider(name = "relationNameTestCases")
    public Object[][] relationNameTestCases() {
        return new Object[][]{
                {FakerDataGenerator.generateName(NameTestDataType.EMPTY), "person.person_name.required"}, // Empty name
                {FakerDataGenerator.generateName(NameTestDataType.MIN_LENGTH), "person.person_name.minLength"}, // Single character
                {FakerDataGenerator.generateName(NameTestDataType.MAX_LENGTH), "person.person_name.maxLength"}, // More than 50 characters
                {FakerDataGenerator.generateName(NameTestDataType.INVALID_FORMAT), "person.person_name.invalidFormat"}, // Invalid characters
                {FakerDataGenerator.generateName(NameTestDataType.VALID), null}  // Valid name
        };
    }

    @DataProvider(name = "pinCodeTestCases")
    public Object[][] pinCodeTestCases() {
        return new Object[][]{
                {PinCodeTestDataType.EMPTY, "person.pinCode.required"},
                {PinCodeTestDataType.LESS_THAN_6_DIGITS, "person.pinCode.invalidLength"},
                {PinCodeTestDataType.MORE_THAN_6_DIGITS, "person.pinCode.invalidLength"},
                {PinCodeTestDataType.STARTS_WITH_ZERO, "person.pinCode.invalidFormat"},
                {PinCodeTestDataType.ALL_ZEROS, "person.pinCode.invalidFormat"},
                {PinCodeTestDataType.STRING_FORMAT, null},
                {PinCodeTestDataType.INT_FORMAT, null},
                {PinCodeTestDataType.VALID, null}
        };
    }

    @DataProvider(name = "emailTestCases")
    public Object[][] emailTestCases() {
        return new Object[][]{
                {EmailTestDataType.EMPTY, "person.email.required"},
                {EmailTestDataType.INVALID_FORMAT, "person.email.invalid"},
                {EmailTestDataType.MISSING_AT_SYMBOL, "person.email.invalid"},
                {EmailTestDataType.MISSING_DOMAIN, "person.email.invalid"},
                {EmailTestDataType.MISSING_USERNAME, "person.email.invalid"},
                {EmailTestDataType.SPECIAL_CHARACTERS, "person.email.invalid"},
                {EmailTestDataType.MULTIPLE_AT_SYMBOLS, "person.email.invalid"},
                {EmailTestDataType.TRAILING_DOT, "person.email.invalid"},
                {EmailTestDataType.LEADING_DOT, "person.email.invalid"},
                {EmailTestDataType.SPACE_IN_EMAIL, "person.email.invalid"},
                {EmailTestDataType.UPPERCASE_EMAIL, null},
                {EmailTestDataType.VALID, null}
        };
    }

    @DataProvider(name = "voterNumberTestCases")
    public static Object[][] voterNumberTestCases() {
        return new Object[][]{
                {VoterNumberTestDataType.EMPTY, "voter_number.required"},
                {VoterNumberTestDataType.LESS_THAN_MIN_LENGTH, "voter_number.minLength"},
                {VoterNumberTestDataType.MORE_THAN_MAX_LENGTH, "voter_number.maxLength"},
                {VoterNumberTestDataType.SPECIAL_CHARACTERS, "voter_number.invalidFormat"},
                {VoterNumberTestDataType.SPACES_INCLUDED, "voter_number.invalidFormat"},
                {VoterNumberTestDataType.WITH_UNDERSCORE_OR_DASH, "voter_number.invalidFormat"},
                {VoterNumberTestDataType.UNICODE_CHARACTERS, "voter_number.invalidFormat"},
                {VoterNumberTestDataType.ZERO_FILLED, "voter_number.invalidFormat"},
                {VoterNumberTestDataType.ONLY_ALPHABETS, null},
                {VoterNumberTestDataType.ONLY_NUMERIC, null},
                {VoterNumberTestDataType.MIXED_CASE, null},
                {VoterNumberTestDataType.VALID_ALPHANUMERIC, null},
                {VoterNumberTestDataType.VALID_EDGE_MIN_LENGTH, null},
                {VoterNumberTestDataType.VALID_EDGE_MAX_LENGTH, null}
        };
    }

    @DataProvider(name = "aadhaarNumberTestCases")
    public static Object[][] aadhaarNumberTestCases() {
        return new Object[][]{
                {AadhaarNumberTestDataType.EMPTY, "aadhaar_number.required"},
                {AadhaarNumberTestDataType.LESS_THAN_12_DIGITS, "aadhaar_number.minLength"},
                {AadhaarNumberTestDataType.MORE_THAN_12_DIGITS, "aadhaar_number.maxLength"},
                {AadhaarNumberTestDataType.EXACTLY_12_DIGITS, null},
                {AadhaarNumberTestDataType.ALPHABETS_INCLUDED, "aadhaar_number.invalidFormat"},
                {AadhaarNumberTestDataType.SPECIAL_CHARACTERS, "aadhaar_number.invalidFormat"},
                {AadhaarNumberTestDataType.SPACES_INCLUDED, "aadhaar_number.invalidFormat"},
                {AadhaarNumberTestDataType.LEADING_ZEROS, null},
                {AadhaarNumberTestDataType.ONLY_SPECIAL_CHARACTERS, "aadhaar_number.invalidFormat"},
                {AadhaarNumberTestDataType.NON_NUMERIC_STRING, "aadhaar_number.invalidFormat"}
        };
    }

    @DataProvider(name = "rationCardTestCases")
    public static Object[][] rationCardTestCases() {
        return new Object[][]{
                {RationCardTestDataType.EMPTY, "ration_card.required"},
                {RationCardTestDataType.LESS_THAN_MIN_LENGTH, "ration_card.minLength"},
                {RationCardTestDataType.MORE_THAN_MAX_LENGTH, "ration_card.maxLength"},
                {RationCardTestDataType.BETWEEN_MIN_AND_MAX_LENGTH, null},
                {RationCardTestDataType.EXACTLY_MIN_LENGTH, null},
                {RationCardTestDataType.EXACTLY_MAX_LENGTH, null},
                {RationCardTestDataType.INTEGER_FORMAT, null},
                {RationCardTestDataType.STRING_ALPHANUMERIC, null}
        };
    }
}
