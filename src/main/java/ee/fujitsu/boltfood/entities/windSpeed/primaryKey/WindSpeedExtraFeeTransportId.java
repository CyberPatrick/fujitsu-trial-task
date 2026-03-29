package ee.fujitsu.boltfood.entities.windSpeed.primaryKey;

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
public class WindSpeedExtraFeeTransportId implements Serializable {
    private static final long serialVersionUID = 2867799872457905014L;
    @NotNull
    @Column(name = "WIND_SPEED_ID", nullable = false)
    private Integer windSpeedId;

    @NotNull
    @Column(name = "TRANSPORT_ID", nullable = false)
    private Integer transportId;


}