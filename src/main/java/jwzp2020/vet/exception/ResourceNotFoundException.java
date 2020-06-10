package jwzp2020.vet.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException   {
    final static Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);


    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Resource not found: " + message + cause);
    }
    public ResourceNotFoundException(String message) {
        super(message);
        logger.error("Resource not found: " + message);

    }
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
        logger.error("Resource not found: "  + cause);
    }

}
