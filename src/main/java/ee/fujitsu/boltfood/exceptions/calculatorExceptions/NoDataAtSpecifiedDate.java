package ee.fujitsu.boltfood.exceptions.calculatorExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoDataAtSpecifiedDate extends RuntimeException {
    public NoDataAtSpecifiedDate() {
        super("No data at specified timestamp.");
    }
}
