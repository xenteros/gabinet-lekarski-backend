package pl.com.gurgul.exception;

/**
 * Created by agurgul on 14.12.2016.
 */
public enum  ErrorMessages {
    MAY_NOT_BE_NULL("may not be null"),
    NO_SUCH_USER("no such user"),
    NOT_ALLOWED("not allowed");

    private String message;

    private ErrorMessages(String s) {
        message = s;
    }
}
