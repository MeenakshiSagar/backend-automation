package enums;

public enum AnniversaryTestDataType {

    EMPTY,                          // Blank anniversary
    VALID_FORMAT,                   // Valid format (e.g., "2010-05-20")
    INVALID_FORMAT,                 // Invalid format like "20-05-2010"
    FUTURE_DATE,                    // Date after today
    ONLY_YEAR,                      // "2015"
    ONLY_MONTH_AND_YEAR,           // "2015-06"
    INVALID_MONTH,                  // "2015-13-20"
    INVALID_DAY,                    // "2015-12-32"
    NON_NUMERIC,                    // "anniv"
    DATE_WITH_TIME,                 // "2015-06-15T10:00:00"
    SPACES_IN_DATE,                 // " 2015 - 06 - 15 "
    SPECIAL_CHARACTERS,            // "2015@06#15"
    ZEROS_IN_DATE                   // "0000-00-00"
}
