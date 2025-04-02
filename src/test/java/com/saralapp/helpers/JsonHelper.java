package com.saralapp.helpers;

import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHelper {

    /**
     * Reads a JSON file from the given file path and returns a JSONObject.
     *
     * @param filePath the path to the JSON file
     * @return JSONObject representing the file content
     */
    public static JSONObject readJsonFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return new JSONObject(content);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
}
