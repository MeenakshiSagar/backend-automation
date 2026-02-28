package utils.faker.generator;

import config.ConfigManager;
import enums.PartyZilaTestDataType;

public class PartyZilaData {

    public static String generate(PartyZilaTestDataType type) {
        return switch (type) {
            case EMPTY -> null;
            case VALID_MAPPED_ID -> ConfigManager.getPartyZilaValidId(); // read from config or env
            case INVALID_NOT_MAPPED_WITH_STATE -> "99999"; // not mapped
            case NON_EXISTENT_ID -> "9999999";
            case INT_FORMAT -> ConfigManager.getPartyZilaValidId();
            case STRING_FORMAT -> ConfigManager.getPartyZilaValidId();
            case SPECIAL_CHARACTERS -> "34@8";
            case LONG_STRING -> "a".repeat(300);
        };
    }
}
