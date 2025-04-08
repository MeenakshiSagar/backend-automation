package enums;

public enum VoterNumberTestDataType {

    EMPTY,                            // 1. Keep blank when mandatory.
    LESS_THAN_MIN_LENGTH,            // 2. Input voter number less than 4 alphanumeric characters.
    MORE_THAN_MAX_LENGTH,            // 3. Input voter number more than 20 alphanumeric characters.
    SPECIAL_CHARACTERS,              // 4. Input special characters in voter number.
    VALID_ALPHANUMERIC,              // 5. Valid voter number between 4 and 20 alphanumeric characters.
    ONLY_NUMERIC,                    // 6. Voter number with only numbers.
    ONLY_ALPHABETS,                  // 7. Voter number with only alphabets.
    MIXED_CASE,                      // 8. Voter number with mixed upper and lower case.
    SPACES_INCLUDED,                 // 9. Voter number with leading/trailing/embedded spaces.
    WITH_UNDERSCORE_OR_DASH,         // 10. Contains underscore or dash symbols (if invalid).
    UNICODE_CHARACTERS,              // 11. Voter number with Unicode characters (e.g., Hindi letters).
    ZERO_FILLED,                     // 12. All zero values (000000)
    VALID_EDGE_MIN_LENGTH,           // 13. Exactly 4 characters (edge valid)
    VALID_EDGE_MAX_LENGTH            // 14. Exactly 20 characters (edge valid)
}
