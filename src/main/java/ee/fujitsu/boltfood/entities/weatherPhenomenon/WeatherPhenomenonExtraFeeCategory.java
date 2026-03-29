package ee.fujitsu.boltfood.entities.weatherPhenomenon;

import ee.fujitsu.boltfood.entities.weatherPhenomenon.primaryKey.WeatherPhenomenonExtraFeeCategoryId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "WEATHER_PHENOMENON_EXTRA_FEE_CATEGORY")
public class WeatherPhenomenonExtraFeeCategory {
    @EmbeddedId
    private WeatherPhenomenonExtraFeeCategoryId id;

    @MapsId("weatherPhenomenonExtraFeeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "WEATHER_PHENOMENON_EXTRA_FEE_ID", nullable = false)
    private WeatherExtraFee weatherPhenomenonExtraFee;

    @MapsId("weatherCategoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "WEATHER_CATEGORY_ID", nullable = false)
    private WeatherCategory weatherCategory;


}