package ee.fujitsu.boltfood.entities.airTemperature.primaryKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class AirTemperatureExtraFeeTransportId implements Serializable {
    private static final long serialVersionUID = 1513415409718751724L;
    @NotNull
    @Column(name = "AIR_TEMPERATURE_ID", nullable = false)
    private Integer airTemperatureId;

    @NotNull
    @Column(name = "TRANSPORT_ID", nullable = false)
    private Integer transportId;


}