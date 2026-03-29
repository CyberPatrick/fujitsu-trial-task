package ee.fujitsu.boltfood.entities.airTemperature;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "AIR_TEMPERATURE_EXTRA_FEE")
public class AirTemperatureExtraFeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "EXTRA_FEE")
    private BigDecimal extraFee;

    @ColumnDefault("FALSE")
    @Column(name = "IS_FORBIDDEN")
    private Boolean isForbidden;

    @Column(name = "HIGHER_BOUND", precision = 4, scale = 1)
    private BigDecimal higherBound;

    @Column(name = "LOWER_BOUND", precision = 4, scale = 1)
    private BigDecimal lowerBound;

    @NotNull
    @ColumnDefault("LOCALTIMESTAMP")
    @Column(name = "TIMESTAMP", nullable = false)
    private Instant timestamp;


}