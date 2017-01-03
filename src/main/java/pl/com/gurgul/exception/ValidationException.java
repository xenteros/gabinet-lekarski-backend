package pl.com.gurgul.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Created by agurgul on 14.12.2016.
 */
public class ValidationException extends RuntimeException {

    private List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        super();
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }
}
