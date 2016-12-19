package pl.com.gurgul.exception;

/**
 * Created by agurgul on 14.12.2016.
 */
public class ValidationError {

    private String field;
    private String message;

    public ValidationError() {
    }

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ValidationError(String field, ErrorMessages errorMessage) {
        this.field = field;
        this.message = errorMessage.toString();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
