package enums;

/**
 * Test data types for the Party Mandal field used by field-level validation tests.
 */
public enum PartyMandalTestDataType {
    EMPTY,

    // Only mandal id provided but zila id missing
    ONLY_MANDAL_PROVIDED_WITHOUT_ZILA,

    // Mandal id provided that does not map to the given zila id
    INVALID_NOT_MAPPED_WITH_ZILA,

    // Mandal id provided correctly mapped to zila id
    VALID_MAPPED_ID,

    // Numeric and string format variations
    INT_FORMAT,
    STRING_FORMAT,

    // Special characters
    SPECIAL_CHARACTERS,

    // Excessively long string
    LONG_STRING
}

