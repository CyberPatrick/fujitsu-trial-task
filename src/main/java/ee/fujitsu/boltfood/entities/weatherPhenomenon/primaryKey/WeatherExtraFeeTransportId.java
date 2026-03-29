package ee.fujitsu.boltfood.entities.weatherPhenomenon.primaryKey;

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
public class WeatherExtraFeeTransportId implements Serializable {
    private static final long serialVersionUID = -8113044460670919188L;
    @NotNull
    @Column(name = "WEATHER_ID", nullable = false)
    private Integer weatherId;

    @NotNull
    @Column(name = "TRANSPORT_ID", nullable = false)
    private Integer transportId;


}