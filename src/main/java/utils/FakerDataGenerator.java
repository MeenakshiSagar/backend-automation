package utils;

import com.github.javafaker.Faker;
import enums.*;
import models.person.PersonRequest;
import utils.faker.generator.AgeData;
import utils.faker.generator.VoterIdData;
import utils.faker.generator.ActiveMemberIdData;
import utils.faker.generator.PartyZilaData;
import utils.faker.generator.PartyMandalData;

import java.time.LocalDate;

public class

FakerDataGenerator {
    private static long phoneCounter = 6591555558L;

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

    // Delegate to AgeData generator
    public static String generateAge(AgeTestDataType type) {
        return AgeData.generate(type);
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

    // Delegate to VoterIdData generator
    public static String generateVoterID(VoterIDTestDataType type) {
        return VoterIdData.generate(type);
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
        person.setAge("25");
        person.setVoterID("ABCDFG8912I");
        return person;
    }


    // Overloaded helper to accept enum or Object test values from DataProviders
    public static PersonRequest getInvalidPerson(String field, Object invalidValue) {
        String valueAsString;
        if (invalidValue == null) {
            valueAsString = null;
        } else if (invalidValue instanceof String) {
            valueAsString = (String) invalidValue;
        } else if (invalidValue instanceof BloodGroupTestDataType) {
            valueAsString = generateBloodGroup((BloodGroupTestDataType) invalidValue);
        } else if (invalidValue instanceof FullAddressTestDataType) {
            valueAsString = generateFullAddress((FullAddressTestDataType) invalidValue);
        } else if (invalidValue instanceof TehsilTestDataType) {
            valueAsString = generateTehsil((TehsilTestDataType) invalidValue);
        } else if (invalidValue instanceof VillageTestDataType) {
            valueAsString = generateVillage((VillageTestDataType) invalidValue);
        } else if (invalidValue instanceof PhoneNumberTestDataType) {
            valueAsString = generatePhone((PhoneNumberTestDataType) invalidValue);
        } else if (invalidValue instanceof PinCodeTestDataType) {
            valueAsString = generatePinCode((PinCodeTestDataType) invalidValue);
        } else if (invalidValue instanceof EmailTestDataType) {
            EmailTestDataType et = (EmailTestDataType) invalidValue;
            switch (et) {
                case EMPTY -> valueAsString = "";
                case VALID -> valueAsString = faker.internet().emailAddress();
                case UPPERCASE_EMAIL -> valueAsString = faker.internet().emailAddress().toUpperCase();
                default -> valueAsString = "invalid_email_example";
            }
        } else if (invalidValue instanceof AadhaarNumberTestDataType) {
            valueAsString = generateAadhaarNumber((AadhaarNumberTestDataType) invalidValue);
        } else if (invalidValue instanceof RationCardTestDataType) {
            valueAsString = generateRationCardNumber((RationCardTestDataType) invalidValue);
        } else if (invalidValue instanceof VoterIDTestDataType) {
            valueAsString = generateVoterID((VoterIDTestDataType) invalidValue);
        } else if (invalidValue instanceof AgeTestDataType) {
            valueAsString = generateAge((AgeTestDataType) invalidValue);
        } else if (invalidValue instanceof enums.ActiveMemberIdTestDataType) {
            enums.ActiveMemberIdTestDataType am = (enums.ActiveMemberIdTestDataType) invalidValue;
            valueAsString = ActiveMemberIdData.generate(am);
        } else if (invalidValue instanceof enums.PartyZilaTestDataType) {
            valueAsString = PartyZilaData.generate((enums.PartyZilaTestDataType) invalidValue);
        } else if (invalidValue instanceof enums.PartyMandalTestDataType) {
            valueAsString = PartyMandalData.generate((enums.PartyMandalTestDataType) invalidValue);
        } else {
            // Fallback to calling toString() for any other enum/object
            valueAsString = invalidValue.toString();
        }

        return getInvalidPerson(field, valueAsString);
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
                break;
            case "email":
                person.setEmail(invalidValue);
                break;
            case "phoneNumber":
                person.setPhoneNumber(invalidValue);
                break;
            case "age":
                person.setAge(invalidValue);
                break;
            case "voter_id":
                person.setVoterID(invalidValue);
                break;
            case "activeMemberId":
                // Active Member ID maps to 'active_member_id' in the PersonRequest model
                person.setActiveMemberID(invalidValue);
                break;
            case "partyZila":
                // party zila id maps to 'party_zila_id' - parse safely
                if (invalidValue == null || invalidValue.isBlank()) {
                    person.setPartyZilaId(null);
                } else {
                    try {
                        person.setPartyZilaId(Integer.valueOf(invalidValue));
                    } catch (NumberFormatException e) {
                        person.setPartyZilaId(null);
                    }
                }
                break;
            case "partyMandal":
                // party mandal id maps to 'party_mandal_id' - parse safely
                if (invalidValue == null || invalidValue.isBlank()) {
                    person.setPartyMandalId(null);
                } else {
                    try {
                        person.setPartyMandalId(Integer.valueOf(invalidValue));
                    } catch (NumberFormatException e) {
                        person.setPartyMandalId(null);
                    }
                }
                break;
            case "full_address":
            case "fullAddress":
                person.setFullAddress(invalidValue);
                break;
            case "tehsil":
            case "taluka":
                person.setTehsil(invalidValue);
                break;
            case "village":
                person.setVillage(invalidValue);
                break;
            case "blood_group":
            case "bloodGroup":
                person.setBloodGroup(invalidValue);
                break;
        }
        return person;
    }


    // New small generators for full address, tehsil, village
    private static String generateFullAddress(FullAddressTestDataType t) {
        switch (t) {
            case VALID_ENGLISH: return "123, Gandhi Nagar, Main Road";
            case VALID_HINDI: return "गाँधी नगर, मुख्य मार्ग";
            case VALID_TAMIL: return "காந்தி நகர், முக்கிய சாலை";
            case LETTERS_AND_NUMBERS: return "Flat 21B, Sector 15, Noida";
            case ALLOWED_PUNCTUATION: return "House No-45/3, Shastri Nagar";
            case MIX_ENGLISH_REGIONAL: return "गाँधी Nagar, Road No 2";
            case EXACTLY_100: return "123 MG Road, Near City Park, Green Valley Apartments, Block B-402, New Delhi, Delhi 110001, India of";
            case OVER_100: return "123 MG Road, Near City Park, Green Valley Apartments, Block B-402, New Delhi, Delhi 110001, India ofx";
            case ONLY_SPACES: return " ";
            case LEADING_TRAILING_SPACES: return " Gandhi Nagar ";
            case ONLY_SPECIAL_CHARACTERS: return "@@@@@@@";
            case EMOJI: return "🏠 Home Address";
            case NULL_VALUE: return null;
            case EMPTY_STRING: return "";
            case MIN_LENGTH: return "A";
            case NEWLINE: return "Gandhi\nNagar";
            case TAB: return "Gandhi\tNagar";
            case MIXED_CASING: return "gAnDhI nAgAr";
            case MULTI_SCRIPT: return "गाँधी Nagar 市场";
            case DIACRITICS: return "São Paulo Nagar";
            case STRESS_DB_ENCODING: return "Árvíztűrő tükörfúrógép – déjà vu, naïve façade, smörgåsbord, São Tomé, Łódź, Αθήνα, القاهرة!";
            default: return "";
        }
    }

    private static String generateTehsil(TehsilTestDataType t) {
        switch (t) {
            case VALID_ENGLISH: return "Taluka One";
            case VALID_HINDI: return "तहसील दो";
            case VALID_TAMIL: return "तेहसील तिन";
            case LETTERS_AND_NUMBERS: return "Taluka 12B";
            case ALLOWED_PUNCTUATION: return "Taluka-No-5/3";
            case MIX_ENGLISH_REGIONAL: return "Taluka गल्ला 2";
            case EXACTLY_100: return "123 MG Road, Near City Park, Green Valley Apartments, Block B-402, New Delhi, Delhi 110001, India of";
            case OVER_100: return "123 MG Road, Near City Park, Green Valley Apartments, Block B-402, New Delhi, Delhi 110001, India ofx";
            case ONLY_SPACES: return " ";
            case LEADING_TRAILING_SPACES: return " Taluka One ";
            case ONLY_SPECIAL_CHARACTERS: return "@@@@@@@";
            case EMOJI: return "🏠 Taluka";
            case NULL_VALUE: return null;
            case EMPTY_STRING: return "";
            case MIN_LENGTH: return "T";
            case NEWLINE: return "Taluka\nOne";
            case TAB: return "Taluka\tOne";
            case MIXED_CASING: return "tAlUkA oNe";
            case MULTI_SCRIPT: return "Taluka नगर 市场";
            case DIACRITICS: return "São Taluka";
            case STRESS_DB_ENCODING: return "Árvíztűrő tükörfúrógép – déjà vu, naïve façade, smörgåsbord, São Tomé, Łódź, Αθήνα, القاهرة!";
            default: return "";
        }
    }

    private static String generateVillage(VillageTestDataType t) {
        switch (t) {
            case VALID_ENGLISH: return "Village One";
            case VALID_HINDI: return "गाँधी गांव";
            case VALID_TAMIL: return "கிராமம் ஒன்று";
            case LETTERS_AND_NUMBERS: return "Village 21A";
            case ALLOWED_PUNCTUATION: return "Village-No-45/3";
            case MIX_ENGLISH_REGIONAL: return "गाँधी Village 2";
            case EXACTLY_100: return "123 MG Road, Near City Park, Green Valley Apartments, Block B-402, New Delhi, Delhi 110001, India of";
            case OVER_100: return "123 MG Road, Near City Park, Green Valley Apartments, Block B-402, New Delhi, Delhi 110001, India ofx";
            case ONLY_SPACES: return " ";
            case LEADING_TRAILING_SPACES: return " Village One ";
            case ONLY_SPECIAL_CHARACTERS: return "@@@@@@@";
            case EMOJI: return "🏠 Village";
            case NULL_VALUE: return null;
            case EMPTY_STRING: return "";
            case MIN_LENGTH: return "V";
            case NEWLINE: return "Village\nOne";
            case TAB: return "Village\tOne";
            case MIXED_CASING: return "vIlLaGe oNe";
            case MULTI_SCRIPT: return "Village गाँधी 市场";
            case DIACRITICS: return "São Village";
            case STRESS_DB_ENCODING: return "Árvíztűrő tükörfúrógép – déjà vu, naïve façade, smörgåsbord, São Tomé, Łódź, Αθήνα, القاهرة!";
            default: return "";
        }
    }

    // Blood Group generator
    private static String generateBloodGroup(enums.BloodGroupTestDataType t) {
        switch (t) {
            case EMPTY: return "";
            case NOT_EXISTS: return "X+";
            case VALID_EXACT_CASE: return "A+";
            case VALID_DIFFERENT_CASE: return "a+";
            default: return "";
        }
    }

}
