package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties props = new Properties();
    private static final Properties adminProps = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config/test-config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }

        try (InputStream ainput = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config/admin-config.properties")) {
            if (ainput != null) adminProps.load(ainput);
        } catch (IOException e) {
            // admin config is optional
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

    public static String getAdminPhone() {
        // Prefer environment variable ADMIN_PHONE if set (useful for CI/secure secrets)
        String envPhone = System.getenv("ADMIN_PHONE");
        if (envPhone != null && !envPhone.isEmpty()) return envPhone;
        return adminProps.getProperty("admin.phone");
    }

    public static String getAdminOtp() {
        // Prefer environment variable ADMIN_OTP if set
        String envOtp = System.getenv("ADMIN_OTP");
        if (envOtp != null && !envOtp.isEmpty()) return envOtp;
        return adminProps.getProperty("admin.otp");
    }

    public static String getPartyZilaValidId() {
        String env = System.getenv("PARTY_ZILA_VALID_ID");
        if (env != null && !env.isEmpty()) return env;
        return adminProps.getProperty("party.zila.valid");
    }

    public static String getPartyMandalValidId() {
        String env = System.getenv("PARTY_MANDAL_VALID_ID");
        if (env != null && !env.isEmpty()) return env;
        return adminProps.getProperty("party.mandal.valid");
    }

}
