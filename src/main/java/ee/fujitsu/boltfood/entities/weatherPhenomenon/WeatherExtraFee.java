package ee.fujitsu.boltfood.entities.weatherPhenomenon;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "WEATHER_EXTRA_FEE")
public class WeatherExtraFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "EXTRA_FEE")
    private Double extraFee;

    @ColumnDefault("FALSE")
    @Column(name = "IS_FORBIDDEN")
    private Boolean isForbidden;

    @NotNull
    @ColumnDefault("LOCALTIMESTAMP")
    @Column(name = "TIMESTAMP", nullable = false)
    private Instant timestamp;


}