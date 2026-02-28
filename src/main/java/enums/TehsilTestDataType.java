package enums;

/**
 * Tehsil uses the same test scenarios as Full Address — keep enum members identical for reuse/consistency.
 */
public enum TehsilTestDataType {
    VALID_ENGLISH,
    VALID_HINDI,
    VALID_TAMIL,
    LETTERS_AND_NUMBERS,
    ALLOWED_PUNCTUATION,
    MIX_ENGLISH_REGIONAL,
    EXACTLY_100,
    OVER_100,
    ONLY_SPACES,
    LEADING_TRAILING_SPACES,
    ONLY_SPECIAL_CHARACTERS,
    EMOJI,
    NULL_VALUE,
    EMPTY_STRING,
    MIN_LENGTH,
    NEWLINE,
    TAB,
    MIXED_CASING,
    MULTI_SCRIPT,
    DIACRITICS,
    STRESS_DB_ENCODING
}
