package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ErrorMessageUtils {
    private static final JsonNode rootNode;

    static {
        ObjectMapper mapper = new ObjectMapper();
        try {
            rootNode = mapper.readTree(new File("src/test/resources/testdata/error_messages.json"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load error_messages.json", e);
        }
    }

    public static String getMessage(String key) {
        if (key == null) return null;
        String[] parts = key.split("\\.");
        JsonNode currentNode = rootNode;
        for (String part : parts) {
            if (currentNode == null || !currentNode.has(part)) {
                return "Key not found: " + key;
            }
            currentNode = currentNode.get(part);
        }
        return currentNode.asText();
    }
}
