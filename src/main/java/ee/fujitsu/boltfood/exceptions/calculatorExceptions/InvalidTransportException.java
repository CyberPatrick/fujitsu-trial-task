package ee.fujitsu.boltfood.exceptions.calculatorExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTransportException extends RuntimeException {
    public InvalidTransportException() {
        super("Server received invalid transport type.");
    }
}
