package enums;

public enum DOBTestDataType {

    EMPTY,                      // Blank DOB
    VALID_FORMAT,               // Valid format (e.g., "1990-12-31")
    INVALID_FORMAT,             // Invalid string (e.g., "31-12-1990")
    YEAR_AFTER_2007,            // e.g., "2010-01-01"
    YEAR_BEFORE_1902,           // e.g., "1900-01-01"
    ONLY_YEAR,                  // e.g., "1995"
    ONLY_MONTH_AND_YEAR,        // e.g., "1995-06"
    INVALID_MONTH,              // e.g., "1995-13-10"
    INVALID_DAY,                // e.g., "1995-12-32"
    VALID_EDGE_CASE,            // e.g., "2007-12-31" or "1902-01-01"
    FUTURE_DATE,                // e.g., tomorrow
    NON_NUMERIC,                // e.g., "abc"
    DATE_WITH_TIME,             // e.g., "1995-06-15T10:00:00"
    SPACES_IN_DATE,             // e.g., " 1995 - 06 - 15 "
    SPECIAL_CHARACTERS          // e.g., "1995@06#15"
}
