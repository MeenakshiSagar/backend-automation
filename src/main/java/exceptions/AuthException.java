package exceptions;

public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);  // Calls the parent constructor with the message
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);  // Allows wrapping another exception
    }


}
