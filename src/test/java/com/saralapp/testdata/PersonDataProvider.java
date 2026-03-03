package com.saralapp.testdata;

import com.saralapp.helpers.ErrorMessagesHelper;
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
                {FakerDataGenerator.generateName(NameTestDataType.EMPTY), "person.person_name.required", "Empty name"}, // Empty name
                {FakerDataGenerator.generateName(NameTestDataType.MIN_LENGTH), "person.person_name.minLength", "Single-character name (min length)"}, // Single character
                {FakerDataGenerator.generateName(NameTestDataType.MAX_LENGTH), "person.person_name.maxLength", "More than max length (>50 chars)"}, // More than 50 characters
                {FakerDataGenerator.generateName(NameTestDataType.INVALID_FORMAT), "person.person_name.invalidFormat", "Invalid format (contains digits/symbols)"}, // Invalid characters
                {FakerDataGenerator.generateName(NameTestDataType.VALID), null, "Valid name"}  // Valid name
        };
    }

    @DataProvider(name = "phoneNumberTestCases")
    public static Object[][] phoneNumberTestCases() {
        return new Object[][]{
                {FakerDataGenerator.generatePhone(PhoneNumberTestDataType.EMPTY), "person.phone_number.required", "Empty phone number"},
                {PhoneNumberTestDataType.MIN_LENGTH, "person.phone_number.minLength", "Too short phone number (<10 digits)"},
                {PhoneNumberTestDataType.MAX_LENGTH, "person.phone_number.maxLength", "Too long phone number (>10 digits)"},
                {PhoneNumberTestDataType.STARTING_FROM_2_TO_4, "person.phone_number.startingFrom2To4", "Phone number starting with 2-4 (invalid)"},
                {PhoneNumberTestDataType.INTEGER_FORMAT, null, "Numeric phone number as integer"},
                {PhoneNumberTestDataType.STRING_FORMAT, null, "Phone number provided as string"},
                {PhoneNumberTestDataType.INVALID_FORMAT, "person.phone_number.invalidFormat", "Invalid phone number format (alpha chars)"},
                {PhoneNumberTestDataType.VALID, null, "Valid phone number"}
        };
    }

    // ------------------ Relation Field Test Cases ------------------
    @DataProvider(name = "relationNameTestCases")
    public Object[][] relationNameTestCases() {
        return new Object[][]{
                {FakerDataGenerator.generateName(NameTestDataType.EMPTY), "person.person_relation_name.required", "Empty relation name"}, // Empty name
                {FakerDataGenerator.generateName(NameTestDataType.MIN_LENGTH), "person.person_relation_name.minLength", "Single-character relation name"}, // Single character
                {FakerDataGenerator.generateName(NameTestDataType.MAX_LENGTH), "person.person_relation_name.maxLength", "Relation name longer than max length (>50)"}, // More than 50 characters
                {FakerDataGenerator.generateName(NameTestDataType.INVALID_FORMAT), "person.person_relation_name.invalidFormat", "Relation name with invalid characters"}, // Invalid characters
                {FakerDataGenerator.generateName(NameTestDataType.VALID), null, "Valid relation name"}  // Valid name
        };
    }

    @DataProvider(name = "pinCodeTestCases")
    public Object[][] pinCodeTestCases() {
        return new Object[][]{
                {PinCodeTestDataType.EMPTY, "person.pinCode.required", "Empty pin code"},
                {PinCodeTestDataType.LESS_THAN_6_DIGITS, "person.pinCode.invalidLength", "Pin code shorter than 6 digits"},
                {PinCodeTestDataType.MORE_THAN_6_DIGITS, "person.pinCode.invalidLength", "Pin code longer than 6 digits"},
                {PinCodeTestDataType.STARTS_WITH_ZERO, "person.pinCode.invalidFormat", "Pin code starting with zero"},
                {PinCodeTestDataType.ALL_ZEROS, "person.pinCode.invalidFormat", "Pin code all zeros"},
                {PinCodeTestDataType.STRING_FORMAT, null, "Pin code as quoted string"},
                {PinCodeTestDataType.INT_FORMAT, null, "Pin code as integer"},
                {PinCodeTestDataType.VALID, null, "Valid pin code"}
        };
    }

    @DataProvider(name = "emailTestCases")
    public Object[][] emailTestCases() {
        return new Object[][]{
                {EmailTestDataType.EMPTY, "person.email.required", "Empty email"},
                {EmailTestDataType.INVALID_FORMAT, "person.email.invalidFormat", "Malformed email"},
                {EmailTestDataType.MISSING_AT_SYMBOL, "person.email.invalidFormat", "Missing '@' symbol"},
                {EmailTestDataType.MISSING_DOMAIN, "person.email.invalidFormat", "Missing domain"},
                {EmailTestDataType.MISSING_USERNAME, "person.email.invalidFormat", "Missing username"},
                {EmailTestDataType.SPECIAL_CHARACTERS, "person.email.invalidFormat", "Email with invalid special characters"},
                {EmailTestDataType.MULTIPLE_AT_SYMBOLS, "person.email.invalidFormat", "Multiple '@' symbols"},
                {EmailTestDataType.TRAILING_DOT, "person.email.invalidFormat", "Trailing dot in domain"},
                {EmailTestDataType.LEADING_DOT, "person.email.invalidFormat", "Leading dot in username"},
                {EmailTestDataType.SPACE_IN_EMAIL, "person.email.invalidFormat", "Space present in email"},
                {EmailTestDataType.UPPERCASE_EMAIL, null, "Uppercase email (allowed)"},
                {EmailTestDataType.VALID, null, "Valid email"}
        };
    }

    @DataProvider(name = "aadhaarNumberTestCases")
    public static Object[][] aadhaarNumberTestCases() {
        return new Object[][]{
                {AadhaarNumberTestDataType.EMPTY, "aadhaar_number.required", "Empty aadhaar"},
                {AadhaarNumberTestDataType.LESS_THAN_12_DIGITS, "aadhaar_number.minLength", "Less than 12 digits"},
                {AadhaarNumberTestDataType.MORE_THAN_12_DIGITS, "aadhaar_number.maxLength", "More than 12 digits"},
                {AadhaarNumberTestDataType.EXACTLY_12_DIGITS, null, "Exactly 12 digits (valid)"},
                {AadhaarNumberTestDataType.ALPHABETS_INCLUDED, "aadhaar_number.invalidFormat", "Alphabets included"},
                {AadhaarNumberTestDataType.SPECIAL_CHARACTERS, "aadhaar_number.invalidFormat", "Special characters included"},
                {AadhaarNumberTestDataType.SPACES_INCLUDED, "aadhaar_number.invalidFormat", "Spaces included"},
                {AadhaarNumberTestDataType.LEADING_ZEROS, null, "Leading zeros allowed"},
                {AadhaarNumberTestDataType.ONLY_SPECIAL_CHARACTERS, "aadhaar_number.invalidFormat", "Only special characters"},
                {AadhaarNumberTestDataType.NON_NUMERIC_STRING, "aadhaar_number.invalidFormat", "Non-numeric string"}
        };
    }

    @DataProvider(name = "rationCardTestCases")
    public static Object[][] rationCardTestCases() {
        return new Object[][]{
                {RationCardTestDataType.EMPTY, "ration_card.required", "Empty ration card"},
                {RationCardTestDataType.LESS_THAN_MIN_LENGTH, "ration_card.minLength", "Short ration card"},
                {RationCardTestDataType.MORE_THAN_MAX_LENGTH, "ration_card.maxLength", "Long ration card"},
                {RationCardTestDataType.BETWEEN_MIN_AND_MAX_LENGTH, null, "Ration card length between min and max"},
                {RationCardTestDataType.EXACTLY_MIN_LENGTH, null, "Ration card exactly min length"},
                {RationCardTestDataType.EXACTLY_MAX_LENGTH, null, "Ration card exactly max length"},
                {RationCardTestDataType.INTEGER_FORMAT, null, "Ration card numeric format"},
                {RationCardTestDataType.STRING_ALPHANUMERIC, null, "Ration card alphanumeric format"}
        };
    }

    @DataProvider(name = "ageTestCases")
    public Object[][] ageTestCases() {
        return new Object[][]{

                {FakerDataGenerator.generateAge(AgeTestDataType.EMPTY), "person.age.required", "Empty age"},
                {FakerDataGenerator.generateAge(AgeTestDataType.LESS_THAN_MIN), "person.age.minValue", "Age less than minimum"},
                {FakerDataGenerator.generateAge(AgeTestDataType.MORE_THAN_MAX), "person.age.maxValue", "Age more than maximum"},
                {FakerDataGenerator.generateAge(AgeTestDataType.EXACT_MIN), null, "Age exactly minimum"},
                {FakerDataGenerator.generateAge(AgeTestDataType.EXACT_MAX), null, "Age exactly maximum"},
                {FakerDataGenerator.generateAge(AgeTestDataType.VALID_RANGE), null, "Age within valid range"},
                {FakerDataGenerator.generateAge(AgeTestDataType.STRING_NUMERIC), null, "Age as numeric string"},
                {FakerDataGenerator.generateAge(AgeTestDataType.STRING_NON_NUMERIC), "person.age.invalidFormat", "Age as non-numeric string"},
                {FakerDataGenerator.generateAge(AgeTestDataType.SPECIAL_CHARACTERS), "person.age.invalidFormat", "Age with special characters"},
                {FakerDataGenerator.generateAge(AgeTestDataType.SPACES), "person.age.required", "Age with spaces only"},
                {FakerDataGenerator.generateAge(AgeTestDataType.DECIMAL_VALUE), "person.age.invalidFormat", "Age as decimal value"},
                {FakerDataGenerator.generateAge(AgeTestDataType.NEGATIVE), "person.age.minValue", "Negative age value"},
                {FakerDataGenerator.generateAge(AgeTestDataType.ZERO), "person.age.minValue", "Zero age"}
        };
    }

    @DataProvider(name = "voterIDTestCases")
    public Object[][] voterIDTestCases() {
        return new Object[][]{
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.EMPTY), "person.voter_id.required", "Empty voter ID"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.NULL_VALUE), "person.voter_id.required", "Null voter ID"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.LESS_THAN_MIN), "person.voter_id.minLength", "Voter ID shorter than min"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.MORE_THAN_MAX), "person.voter_id.maxLength", "Voter ID longer than max"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.INVALID_SPECIAL_CHAR), "person.voter_id.invalidFormat", "Invalid special char in voter ID"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.SPACE_IN_INPUT), "person.voter_id.invalidFormat", "Space in voter ID"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.LEADING_TRAILING_SPACES), "person.voter_id.invalidFormat", "Leading/trailing spaces in voter ID"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.UNICODE_CHARACTERS), "person.voter_id.invalidFormat", "Unicode characters in voter ID"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.MIXED_CASE), null, "Mixed case voter ID (allowed)"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.NUMERIC_ONLY), null, "Numeric-only voter ID (allowed)"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.ALPHABET_ONLY), null, "Alphabet-only voter ID (allowed)"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.FORWARD_SLASH), null, "Voter ID with forward slash (allowed)"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.MULTIPLE_FORWARD_SLASH), null, "Voter ID with multiple forward slashes (allowed)"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.BACKWARD_SLASH), null, "Voter ID with backward slash (allowed)"}, // Allowed as per note
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.VALID_10_CHAR), null, "Valid 10-char voter ID"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.VALID_16_CHAR), null, "Valid 16-char voter ID"},
                {FakerDataGenerator.generateVoterID(VoterIDTestDataType.VALID), null, "Valid voter ID"}
        };
    }

    // ------------------ Active Member Id Field Test Cases ------------------
    @DataProvider(name = "activeMemberIdTestCases")
    public Object[][] activeMemberIdTestCases() {
        return new Object[][]{
                // 1. Keep Active Membership blank when mandatory -> should fail with required
                {"", "person.active_member_id.required", "Empty Active Member ID"},
                // 2. Input only alphabets (e.g., "ActiveMember") -> should pass
                {"ActiveMember", null, "Alphabets-only Active Member ID"},
                // 3. Input only numbers (e.g., "12345") -> should fail (as per user's note it should fail, but spec mapping says accepted 200; following user's final mapping: numbers should fail)
                {"12345", "person.active_member_id.invalidFormat", "Numeric-only Active Member ID (invalid)"},
                // 4. Input alphanumeric value (e.g., "Member123") -> should fail
                {"Member123", "person.active_member_id.invalidFormat", "Alphanumeric Active Member ID (invalid)"},
                // 5. Input with special characters (e.g., "Member-Active@") -> should fail
                {"Member-Active@", "person.active_member_id.invalidFormat", "Active Member ID with special characters (invalid)"},
                // 6. Input with spaces (e.g., "Active Member") -> accepted per earlier line, but mapping says accepted 200 -> treat as valid
                {"Active Member", null, "Active Member ID with spaces (allowed)"},
                // 7. Input with special characters at the start (e.g., "-ActiveMember") -> should fail
                {"-ActiveMember", "person.active_member_id.invalidFormat", "Leading special character (invalid)"},
                // 8. Input with only special characters (e.g., "@#$%^&*") -> should fail
                {"@#$%^&*", "person.active_member_id.invalidFormat", "Only special characters (invalid)"},
                // 9. Input with excessively long string (>50 chars) -> should fail
                {"Health Minister kjssad asdjhsa dasjdh sadjkashd jja", "person.active_member_id.invalidFormat", "Too long Active Member ID (>50)"},
                // 10. Input with exactly 50 characters -> should pass
                {"Health Minister kjssad asdjhsa dasjdh sadjkashd ja", null, "Exactly 50-character Active Member ID (allowed)"},
                // 11. Input with leading and trailing spaces (" ActiveMember ") -> should be trimmed/validated correctly -> both trimmed now, treat as valid
                {" ActiveMember ", null, "Leading/trailing spaces in Active Member ID (trimmed)"}
        };
    }

    // ------------------ Party Zila Field Test Cases ------------------
    @DataProvider(name = "partyZilaTestCases")
    public Object[][] partyZilaTestCases() {
        return new Object[][]{
                // 1. Keep party zila blank when mandatory -> expected required
                {PartyZilaTestDataType.EMPTY, "person.party_zila.required", "Empty Party Zila (mandatory)"},
                // 2. Input party zila id which not mapped with provided state id -> invalid selection
                {PartyZilaTestDataType.INVALID_NOT_MAPPED_WITH_STATE, "person.party_zila.invalidSelection", "Unmapped Party Zila ID (invalid selection)"},
                // 3. Input party zila id which mapped with provided state id -> accepted
                {PartyZilaTestDataType.VALID_MAPPED_ID, null, "Valid Party Zila ID mapped to state"},
                // 4. Input party zila id in string format -> accepted (backend should accept numeric in string)
                {PartyZilaTestDataType.STRING_FORMAT, null, "Party Zila ID as string (allowed)"}
        };
    }

    // ------------------ Party Mandal Field Test Cases ------------------
    @DataProvider(name = "partyMandalTestCases")
    public Object[][] partyMandalTestCases() {
        return new Object[][]{
                // 1. Keep party mandal blank when mandatory -> expected required
                {PartyMandalTestDataType.EMPTY, "person.party_mandal.required", "Empty Party Mandal (mandatory)"},
                // 2. Input only party mandal id, keep zila id null -> should cause party zila required
                {PartyMandalTestDataType.ONLY_MANDAL_PROVIDED_WITHOUT_ZILA, "person.party_zila.required", "Mandal provided without Zila (should require Zila)"},
                // 3. Input party mandal id which not mapped with provided zila id -> invalid mapping
                {PartyMandalTestDataType.INVALID_NOT_MAPPED_WITH_ZILA, "person.party_mandal.invalidMapping", "Mandal ID not mapped to given Zila (invalid mapping)"},
                // 4. Input party mandal id which mapped with provided zila id -> accepted
                {PartyMandalTestDataType.VALID_MAPPED_ID, null, "Valid Party Mandal ID mapped to Zila"},
                // 5. Input party mandal in string format -> accepted
                {PartyMandalTestDataType.STRING_FORMAT, null, "Party Mandal ID as string (allowed)"}
        };
    }

    @DataProvider(name = "fullAddressTestCases")
    public Object[][] fullAddressTestCases() {
        return new Object[][]{
                // Positive (use enums so FakerDataGenerator produces the value)
                {FullAddressTestDataType.VALID_ENGLISH, null, "Valid English input within limit"},
                {FullAddressTestDataType.VALID_HINDI, null, "Valid Hindi input"},
                {FullAddressTestDataType.VALID_TAMIL, null, "Valid Tamil input"},
                {FullAddressTestDataType.LETTERS_AND_NUMBERS, null, "Mixed letters+numbers"},
                {FullAddressTestDataType.ALLOWED_PUNCTUATION, null, "Allowed punctuation"},
                {FullAddressTestDataType.MIX_ENGLISH_REGIONAL, null, "Mix English+regional"},
                {FullAddressTestDataType.EXACTLY_100, null, "Exactly 100 chars"},
                // Negative
                {FullAddressTestDataType.OVER_100, "person.full_address.maxLength", "Exceeds max length (>100)"},
                {FullAddressTestDataType.ONLY_SPACES, "person.full_address.required", "Only spaces"},
                {FullAddressTestDataType.LEADING_TRAILING_SPACES, null, "Leading/trailing spaces"},
                {FullAddressTestDataType.ONLY_SPECIAL_CHARACTERS, "person.full_address.invalidFormat", "Only special characters"},
                {FullAddressTestDataType.EMOJI, "person.full_address.invalidCharacters", "Contains emoji"},
                {FullAddressTestDataType.NULL_VALUE, "person.full_address.required", "Null value"},
                {FullAddressTestDataType.EMPTY_STRING, "person.full_address.required", "Empty string"},
                // Edge cases
                {FullAddressTestDataType.MIN_LENGTH, null, "Minimum valid length"},
                {FullAddressTestDataType.NEWLINE, "person.full_address.newlineNotAllowed", "Contains newline"},
                {FullAddressTestDataType.TAB, "person.full_address.invalidFormat", "Contains tab"},
                {FullAddressTestDataType.MIXED_CASING, null, "Mixed casing preserved"},
                {FullAddressTestDataType.MULTI_SCRIPT, null, "Multiple scripts allowed"},
                {FullAddressTestDataType.DIACRITICS, null, "Diacritical marks allowed"},
                {FullAddressTestDataType.STRESS_DB_ENCODING, null, "Database encoding stress test"}
        };
    }

    @DataProvider(name = "tehsilTestCases")
    public Object[][] tehsilTestCases() {
        return new Object[][]{
                {TehsilTestDataType.VALID_ENGLISH, null, "Valid tehsil English"},
                {TehsilTestDataType.VALID_HINDI, null, "Valid tehsil Hindi"},
                {TehsilTestDataType.MIN_LENGTH, null, "Minimum length tehsil"},
                {TehsilTestDataType.EXACTLY_100, null, "Exactly 100 chars tehsil"},
                {TehsilTestDataType.OVER_100, "person.tehsil.maxLength", "Too long tehsil (>100)"},
                {TehsilTestDataType.ONLY_SPACES, "person.tehsil.required", "Only spaces"},
                {TehsilTestDataType.LEADING_TRAILING_SPACES, null, "Leading/trailing spaces"},
                {TehsilTestDataType.NULL_VALUE, "person.tehsil.required", "Null tehsil"},
                {TehsilTestDataType.EMPTY_STRING, "person.tehsil.required", "Empty tehsil string"}
        };
    }

    @DataProvider(name = "villageTestCases")
    public Object[][] villageTestCases() {
        return new Object[][]{
                {VillageTestDataType.VALID_ENGLISH, null, "Valid village"},
                {VillageTestDataType.VALID_HINDI, null, "Valid village Hindi"},
                {VillageTestDataType.MIN_LENGTH, null, "Minimum length village"},
                {VillageTestDataType.EXACTLY_100, null, "Exactly 100 chars village"},
                {VillageTestDataType.OVER_100, "person.village.maxLength", "Too long village (>100)"},
                {VillageTestDataType.ONLY_SPACES, "person.village.required", "Only spaces"},
                {VillageTestDataType.LEADING_TRAILING_SPACES, null, "Leading/trailing spaces"},
                {VillageTestDataType.NULL_VALUE, "person.village.required", "Null village"},
                {VillageTestDataType.EMPTY_STRING, "person.village.required", "Empty village string"}
        };
    }

    @DataProvider(name = "bloodGroupTestCases")
    public Object[][] bloodGroupTestCases() {
        return new Object[][]{
                // Empty mandatory
                {BloodGroupTestDataType.EMPTY, "person.blood_group.required", "Keep blood group empty when mandatory"},
                // Non-existent value
                {BloodGroupTestDataType.NOT_EXISTS, "person.blood_group.invalid", "Input blood group which does not exist in defined set"},
                // Valid exact values (each should be accepted)
                {"A+", null, "Valid blood group A+"},
                {"A-", null, "Valid blood group A-"},
                {"B+", null, "Valid blood group B+"},
                {"B-", null, "Valid blood group B-"},
                {"AB+", null, "Valid blood group AB+"},
                {"AB-", null, "Valid blood group AB-"},
                {"O+", null, "Valid blood group O+"},
                {"O-", null, "Valid blood group O-"},
                // Case variant (lowercase) should be rejected if backend is case-sensitive
                {BloodGroupTestDataType.VALID_DIFFERENT_CASE, "person.blood_group.invalid", "Valid blood group but different case (case sensitive)"}
        };
    }

}
