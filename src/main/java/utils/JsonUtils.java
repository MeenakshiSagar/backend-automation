package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static String toJson(Object obj) {
        if (obj == null) return "null";
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            return "{\"error\":\"serialization_failed\"}";
        }
    }
}
