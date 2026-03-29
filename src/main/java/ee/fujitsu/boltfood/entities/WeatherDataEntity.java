package ee.fujitsu.boltfood.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "weather_data")
public class WeatherDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Size(max = 255)
    @NotNull
    @Column(name = "STATION_NAME", nullable = false)
    private String stationName;
    @Column(name = "WMO_CODE")
    private Integer wmoCode;
    @Column(name = "AIR_TEMPERATURE")
    private Float airTemperature;
    @Column(name = "WIND_SPEED")
    private Float windSpeed;
    @Size(max = 255)
    @Column(name = "WEATHER_PHENOMENON")
    private String weatherPhenomenon;
    @NotNull
    @JdbcTypeCode(SqlTypes.TIMESTAMP_WITH_TIMEZONE)
    @Column(name = "TIMESTAMP", nullable = false)
    private Instant timestamp;
}
