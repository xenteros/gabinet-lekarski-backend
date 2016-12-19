package pl.com.gurgul.exception;

import java.util.List;

/**
 * Created by agurgul on 14.12.2016.
 */
public class ValidationException extends RuntimeException {

    List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        super("ValidationException");
        this.errors = errors;
    }
}
