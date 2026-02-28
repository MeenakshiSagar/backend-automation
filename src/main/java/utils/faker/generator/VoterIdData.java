package utils.faker.generator;

import com.github.javafaker.Faker;
import enums.VoterIDTestDataType;

public class VoterIdData {
    private static final Faker faker = new Faker();

    public static String generate(VoterIDTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case NULL_VALUE -> null;
            case LESS_THAN_MIN -> faker.regexify("[A-Z0-9]{8}");
            case MORE_THAN_MAX -> faker.regexify("[A-Z0-9]{17}");
            case MIN_LENGTH, VALID_10_CHAR -> faker.regexify("[A-Z0-9]{10}");
            case MAX_LENGTH, VALID_16_CHAR -> faker.regexify("[A-Z0-9]{16}");
            case LOWERCASE -> faker.regexify("[a-z0-9]{10}");
            case NUMERIC_ONLY -> faker.number().digits(10);
            case ALPHABET_ONLY -> faker.regexify("[A-Z]{10}");
            case FORWARD_SLASH -> "AB12/CD34EF";
            case MULTIPLE_FORWARD_SLASH -> "AB/12/CD34E";
            case BACKWARD_SLASH -> "9A8B\\7C6D\\5E4F";
            case INVALID_SPECIAL_CHAR -> "ABC@1234DE";
            case SPACE_IN_INPUT -> "ABC 1234DE";
            case LEADING_TRAILING_SPACES -> "  AB12CD34EF  ";
            case UNICODE_CHARACTERS -> "AB12ＣＤ34ＥＦ"; // full-width characters
            case MIXED_CASE -> "aB12Cd34eF";
            case VALID -> "AB12CD34EF";
        };
    }
}
