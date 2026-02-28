package utils.faker.generator;

import com.github.javafaker.Faker;
import enums.AgeTestDataType;

public class AgeData {
    private static final Faker faker = new Faker();

    public static String generate(AgeTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case LESS_THAN_MIN -> String.valueOf(Faker.instance().number().numberBetween(0, 17));
            case MORE_THAN_MAX -> String.valueOf(Faker.instance().number().numberBetween(124, 150));
            case EXACT_MIN -> "18";
            case EXACT_MAX -> "123";
            case VALID_RANGE -> String.valueOf(Faker.instance().number().numberBetween(19, 122));
            case STRING_NUMERIC -> String.valueOf(Faker.instance().number().numberBetween(18, 99));
            case STRING_NON_NUMERIC -> "eighteen";
            case SPECIAL_CHARACTERS -> "@ge#";
            case SPACES -> "  ";
            case DECIMAL_VALUE -> "25.5";
            case NEGATIVE -> "-25";
            case ZERO -> "0";
        };
    }
}
