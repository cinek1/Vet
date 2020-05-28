package jwzp2020.vet.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class WrongDateOrTimeException extends RuntimeException {
    public WrongDateOrTimeException() {
        super();
    }
    public WrongDateOrTimeException(String message, Throwable cause) {
        super(message, cause);
    }
    public WrongDateOrTimeException(String message) {
        super(message);
    }
    public WrongDateOrTimeException(Throwable cause) {
        super(cause);
    }

}
