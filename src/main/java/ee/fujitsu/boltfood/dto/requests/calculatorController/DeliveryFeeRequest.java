package ee.fujitsu.boltfood.dto.requests.calculatorController;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

public record DeliveryFeeRequest(
        @NotBlank @NotNull String city,
        @NotBlank @NotNull String transportType,
        @NotBlank @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timestamp
        ) {
}
