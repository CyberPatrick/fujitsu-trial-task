package ee.fujitsu.boltfood.exceptions.calculatorExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCityException extends RuntimeException {
    public InvalidCityException() {
        super("Server received invalid city.");
    }
}
