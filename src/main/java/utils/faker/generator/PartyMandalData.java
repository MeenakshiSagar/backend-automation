package utils.faker.generator;

import enums.PartyMandalTestDataType;

public class PartyMandalData {

    public static String generate(PartyMandalTestDataType type) {
        return switch (type) {
            case EMPTY -> null;
            case ONLY_MANDAL_PROVIDED_WITHOUT_ZILA -> "101";
            case INVALID_NOT_MAPPED_WITH_ZILA -> "99999";
            case VALID_MAPPED_ID -> "5360";
            case INT_FORMAT -> "5360";
            case STRING_FORMAT -> "5360";
            case SPECIAL_CHARACTERS -> "5@360";
            case LONG_STRING -> "b".repeat(300);
        };
    }
}
