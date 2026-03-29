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
public class WeatherPhenomenonExtraFeeCategoryId implements Serializable {
    private static final long serialVersionUID = 885969093101473432L;
    @NotNull
    @Column(name = "WEATHER_PHENOMENON_EXTRA_FEE_ID", nullable = false)
    private Integer weatherPhenomenonExtraFeeId;

    @NotNull
    @Column(name = "WEATHER_CATEGORY_ID", nullable = false)
    private Integer weatherCategoryId;


}