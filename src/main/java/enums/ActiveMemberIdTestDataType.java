// filepath: c:\Users\Meenakshi\IdeaProjects\backend-automation\src\main\java\enums\ActiveMemberIdTestDataType.java
package enums;

/**
 * Test data types for Active Member ID field validation.
 * Use these in data providers or generators when you want structured test cases.
 */
public enum ActiveMemberIdTestDataType {
    EMPTY,
    ALPHABET_ONLY,
    NUMERIC_ONLY,
    ALPHANUMERIC,
    SPECIAL_CHARACTERS,
    SPACES_IN_INPUT,
    LEADING_TRAILING_SPACES,
    STARTS_WITH_SPECIAL,
    ONLY_SPECIAL_CHARACTERS,
    TOO_LONG,
    EXACT_50,
    VALID
}

