package ee.fujitsu.boltfood.controllersAdvices;

import ee.fujitsu.boltfood.controllers.CalculatorController;
import ee.fujitsu.boltfood.dto.responses.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = CalculatorController.class)
public class CalculatorExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handle(RuntimeException e) {
        ResponseStatus statusAnnotation = AnnotationUtils.findAnnotation(
                e.getClass(),
                ResponseStatus.class
        );

        HttpStatus status;
        String responseMessage;
        if (statusAnnotation == null) {
            log.error("CalculatorExceptionHandler received unknown error without annotation", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage = "Unknown exception";
        } else {
            status = statusAnnotation.value();
            responseMessage = e.getMessage();

            if (status.is5xxServerError()) {
                log.warn("Occurred server exception", e);
            }
        }

        return ResponseEntity.status(status)
                .body(new ExceptionResponse(
                        status.value(),
                        status.getReasonPhrase(),
                        responseMessage
                ));
    }
}
