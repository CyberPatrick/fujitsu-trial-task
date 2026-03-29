package ee.fujitsu.boltfood.entities.airTemperature;

import ee.fujitsu.boltfood.entities.TransportEntity;
import ee.fujitsu.boltfood.entities.airTemperature.primaryKey.AirTemperatureExtraFeeTransportId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "AIR_TEMPERATURE_EXTRA_FEE_TRANSPORT")
public class AirTemperatureExtraFeeTransport {
    @EmbeddedId
    private AirTemperatureExtraFeeTransportId id;

    @MapsId("airTemperatureId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "AIR_TEMPERATURE_ID", nullable = false)
    private AirTemperatureExtraFeeEntity airTemperature;

    @MapsId("transportId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "TRANSPORT_ID", nullable = false)
    private TransportEntity transport;


}