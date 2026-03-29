package ee.fujitsu.boltfood.entities.stationCityConnection.primaryKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class StationCityConnectionId implements Serializable {
    private static final long serialVersionUID = -3337121973008319396L;
    @Size(max = 255)
    @NotNull
    @Column(name = "STATION_NAME", nullable = false)
    private String stationName;

    @Size(max = 255)
    @NotNull
    @Column(name = "CITY_NAME", nullable = false)
    private String cityName;


}