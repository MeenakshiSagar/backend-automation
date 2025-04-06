package com.saralapp.helpers;

import org.json.JSONObject;

public class ErrorMessagesHelper {
    private static final JSONObject errorMessages;

    static {
        errorMessages = JsonHelper.readJsonFile("src/test/resources/testdata/error_messages.json");

    }

    /**
     * Retrieves an error message based on a dot-delimited key.
     * Replaces placeholders (e.g., {{NAME_INVALID_FORMAT}}) with values from ErrorMessagesConstants.
     *
     * @param key the error message key, e.g., "person.person_name.minLength"
     * @return the error message string with placeholders replaced
     */
    public static String getErrorMessage(String key) {
        System.out.println("JSON Object of error messages: " + errorMessages);
        String[] keys = key.split("\\.");
        JSONObject current = errorMessages;
        for (int i = 0; i < keys.length; i++) {
            if (current.has(keys[i])) {
                if (i == keys.length - 1) {
                    String message = current.getString(keys[i]);
                    // Replace known placeholders with constant values
                    if (message.contains("{{NAME_INVALID_FORMAT}}")) {
                        message = message.replace("{{NAME_INVALID_FORMAT}}", ErrorMessagesConstants.NAME_INVALID_FORMAT);
                    }
                    // You can add more placeholder replacements here if needed.
                    return message;
                } else {
                    current = current.getJSONObject(keys[i]);
                }
            } else {
                return "Error message not found for key: " + key;
            }
        }
        return "";
    }
}
