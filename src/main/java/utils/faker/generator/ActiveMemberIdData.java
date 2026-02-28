package utils.faker.generator;

import com.github.javafaker.Faker;
import enums.ActiveMemberIdTestDataType;

public class ActiveMemberIdData {
    private static final Faker faker = new Faker();

    public static String generate(ActiveMemberIdTestDataType type) {
        return switch (type) {
            case EMPTY -> "";
            case ALPHABET_ONLY -> faker.letterify("????????");
            case NUMERIC_ONLY -> faker.number().digits(5);
            case ALPHANUMERIC -> faker.bothify("????####");
            case SPECIAL_CHARACTERS -> "Member-Active@";
            case SPACES_IN_INPUT -> "Active Member";
            case LEADING_TRAILING_SPACES -> " ActiveMember ";
            case STARTS_WITH_SPECIAL -> "-ActiveMember";
            case ONLY_SPECIAL_CHARACTERS -> "@#$%^&*";
            case TOO_LONG -> faker.lorem().characters(60);
            case EXACT_50 -> faker.lorem().characters(50);
            case VALID -> "ActiveMember";
        };
    }
}

