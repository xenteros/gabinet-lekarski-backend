package pl.com.gurgul.exception;

/**
 * Created by agurgul on 03.01.2017.
 */
public class ErrorWrapper {
    private String message;

    public ErrorWrapper(String message) {
        this.message = message;
    }

    public ErrorWrapper() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
