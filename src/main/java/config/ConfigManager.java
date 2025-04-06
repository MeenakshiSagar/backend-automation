package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config/test-config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public static String getBaseUrl() {
        return props.getProperty("api.base.url");
    }

    public static String getPhone() {
        return props.getProperty("test.phone");
    }

    public static String getEmail() {
        return props.getProperty("test.email");
    }

    public static String getPassword() {
        return props.getProperty("test.password");
    }

    public static String getTestOtp() {
        return props.getProperty("test.otp");
    }

}
