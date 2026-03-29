package ee.fujitsu.boltfood.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ee.fujitsu.boltfood.entities.BaseFeeEntity}
 */
public record BaseFeeDto(@NotNull @Size(max = 255) String city,
                         @NotNull Double regionalBaseFee,
                         @NotNull Instant timestamp) {
}