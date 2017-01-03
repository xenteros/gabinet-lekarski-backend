package pl.com.gurgul.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by agurgul on 03.01.2017.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorWrapper> RunTimeException(RuntimeException e) {
        LOG.error("Error: {}", e.getMessage());
        return new ResponseEntity<ErrorWrapper>(new ErrorWrapper(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseEntity<List<ValidationError>> validationExceptionHandle(ValidationException e) {
        return new ResponseEntity<List<ValidationError>>(e.getErrors(), HttpStatus.BAD_REQUEST);
    }
}
