package enums;

/**
 * Test data types for the Party Zila field used by field-level validation tests.
 * Use these enums in data providers to indicate which scenario is being exercised.
 */
public enum PartyZilaTestDataType {
    // No value provided when the field is mandatory
    EMPTY,

    // A valid party zila id that is correctly mapped with the provided state
    VALID_MAPPED_ID,

    // A party zila id that exists but is not mapped with the provided state
    INVALID_NOT_MAPPED_WITH_STATE,

    // A party zila id that does not exist in the system
    NON_EXISTENT_ID,

    // Numeric id provided (int format)
    INT_FORMAT,

    // Id provided as string when numeric is expected
    STRING_FORMAT,

    // Id contains special characters
    SPECIAL_CHARACTERS,

    // Excessively long id value
    LONG_STRING
}

