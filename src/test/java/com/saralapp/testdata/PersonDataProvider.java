package com.saralapp.testdata;

import enums.*;
import models.person.PersonRequest;
import org.testng.annotations.DataProvider;
import utils.FakerDataGenerator;
import utils.FormFilterData;

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

    @DataProvider(name = "phoneNumberTestCases")
    public static Object[][] phoneNumberTestCases() {
        return new Object[][]{
                {FakerDataGenerator.generatePhone(PhoneNumberTestDataType.EMPTY), "person.phone_number.required"},
                {PhoneNumberTestDataType.MIN_LENGTH, "person.phone_number.minLength"},
                {PhoneNumberTestDataType.MAX_LENGTH, "person.phone_number.maxLength"},
                {PhoneNumberTestDataType.STARTING_FROM_2_TO_4, "person.phone_number.invalidFormat"},
                {PhoneNumberTestDataType.INTEGER_FORMAT, null},
                {PhoneNumberTestDataType.STRING_FORMAT, null},
                {PhoneNumberTestDataType.INVALID_FORMAT, "person.phone_number.invalidFormat"},
                {PhoneNumberTestDataType.VALID, null}
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

    @DataProvider(name = "ageTestCases")
    public Object[][] ageTestCases() {
        return new Object[][]{

                {FakerDataGenerator.generateAge(AgeTestDataType.EMPTY), "age.required"},
                {FakerDataGenerator.generateAge(AgeTestDataType.LESS_THAN_MIN), "age.minValue"},
                {FakerDataGenerator.generateAge(AgeTestDataType.MORE_THAN_MAX), "age.maxValue"},
                {FakerDataGenerator.generateAge(AgeTestDataType.EXACT_MIN), null},
                {FakerDataGenerator.generateAge(AgeTestDataType.EXACT_MAX), null},
                {FakerDataGenerator.generateAge(AgeTestDataType.VALID_RANGE), null},
                {FakerDataGenerator.generateAge(AgeTestDataType.STRING_NUMERIC), null},
                {FakerDataGenerator.generateAge(AgeTestDataType.STRING_NON_NUMERIC), "age.invalidFormat"},
                {FakerDataGenerator.generateAge(AgeTestDataType.SPECIAL_CHARACTERS), "age.invalidFormat"},
                {FakerDataGenerator.generateAge(AgeTestDataType.SPACES), "age.required"},
                {FakerDataGenerator.generateAge(AgeTestDataType.DECIMAL_VALUE), "age.invalidFormat"},
                {FakerDataGenerator.generateAge(AgeTestDataType.NEGATIVE), "age.minValue"},
                {FakerDataGenerator.generateAge(AgeTestDataType.ZERO), "age.minValue"}
        };
    }

    @DataProvider(name = "voterIDTestCases")
    public Object[][] voterIDTestCases() {
        return new Object[][]{
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.EMPTY), "voter_id.required"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.NULL_VALUE), "voter_id.required"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.LESS_THAN_MIN), "voter_id.minLength"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.MORE_THAN_MAX), "voter_id.maxLength"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.INVALID_SPECIAL_CHAR), "voter_id.invalidFormat"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.SPACE_IN_INPUT), "voter_id.invalidFormat"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.LEADING_TRAILING_SPACES), "voter_id.invalidFormat"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.UNICODE_CHARACTERS), "voter_id.invalidFormat"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.MIXED_CASE), null},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.NUMERIC_ONLY), null},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.ALPHABET_ONLY), null},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.FORWARD_SLASH), null},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.MULTIPLE_FORWARD_SLASH), null},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.BACKWARD_SLASH), null}, // Allowed as per note
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.VALID_10_CHAR), null},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.VALID_16_CHAR), null},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.VALID), null}
        };
    }
}
