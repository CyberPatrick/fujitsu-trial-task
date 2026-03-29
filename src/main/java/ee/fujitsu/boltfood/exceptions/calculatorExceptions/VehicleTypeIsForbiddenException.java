package ee.fujitsu.boltfood.exceptions.calculatorExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class VehicleTypeIsForbiddenException extends RuntimeException {
    private final static String exceptionMessage = "Usage of selected vehicle type is forbidden";
    public VehicleTypeIsForbiddenException() {
        super(exceptionMessage);
    }
}
