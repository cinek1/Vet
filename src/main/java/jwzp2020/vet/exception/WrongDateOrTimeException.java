package jwzp2020.vet.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class WrongDateOrTimeException extends RuntimeException {

    final static Logger logger = LoggerFactory.getLogger(WrongDateOrTimeException.class);


    public WrongDateOrTimeException() {
        super();
    }
    public WrongDateOrTimeException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Wrong data " + message + cause);

    }
    public WrongDateOrTimeException(String message) {
        super(message);
        logger.error("Wrong data" + message);

    }
    public WrongDateOrTimeException(Throwable cause) {
        super(cause);
        logger.error("Wrong data "  + cause);

    }

}
