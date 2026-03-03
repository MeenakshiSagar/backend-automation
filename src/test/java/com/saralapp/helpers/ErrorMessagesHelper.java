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
        // System.out.println("JSON Object of error messages: " + errorMessages); // Commented out debug print
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
                    if (message.contains("{{RELATION_NAME_INVALID_FORMAT}}")) {
                        message = message.replace("{{RELATION_NAME_INVALID_FORMAT}}", ErrorMessagesConstants.RELATION_NAME_INVALID_FORMAT);
                    }
                    if (message.contains("{{PHONE_NUMBER_INVALID_FORMAT}}")) {
                        message = message.replace("{{PHONE_NUMBER_INVALID_FORMAT}}", ErrorMessagesConstants.PHONE_NUMBER_INVALID_FORMAT);
                    }
                    if (message.contains("{{EMAIL_INVALID_FORMAT}}")) {
                        message = message.replace("{{EMAIL_INVALID_FORMAT}}", ErrorMessagesConstants.EMAIL_INVALID_FORMAT);
                    }
                    if (message.contains("{{EMAIL_REQUIRED}}")) {
                        message = message.replace("{{EMAIL_REQUIRED}}", ErrorMessagesConstants.EMAIL_REQUIRED);
                    }
                    if (message.contains("{{AGE_INVALID_FORMAT}}")) {
                       message = message.replace("{{AGE_INVALID_FORMAT}}", ErrorMessagesConstants.AGE_INVALID_FORMAT);
                    }
                    if (message.contains("{{VOTER_ID_INVALID_FORMAT}}")){
                        message = message.replace("{{VOTER_ID_INVALID_FORMAT}}", ErrorMessagesConstants.VOTER_ID_INVALID_FORMAT);
                    }
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
