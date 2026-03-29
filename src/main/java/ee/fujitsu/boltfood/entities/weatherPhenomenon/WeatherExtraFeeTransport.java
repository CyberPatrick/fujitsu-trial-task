package ee.fujitsu.boltfood.entities.weatherPhenomenon;

import ee.fujitsu.boltfood.entities.TransportEntity;
import ee.fujitsu.boltfood.entities.weatherPhenomenon.primaryKey.WeatherExtraFeeTransportId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "WEATHER_EXTRA_FEE_TRANSPORT")
public class WeatherExtraFeeTransport {
    @EmbeddedId
    private WeatherExtraFeeTransportId id;

    @MapsId("weatherId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "WEATHER_ID", nullable = false)
    private WeatherExtraFee weather;

    @MapsId("transportId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "TRANSPORT_ID", nullable = false)
    private TransportEntity transport;


}