package ee.fujitsu.boltfood.entities.windSpeed;

import ee.fujitsu.boltfood.entities.TransportEntity;
import ee.fujitsu.boltfood.entities.windSpeed.primaryKey.WindSpeedExtraFeeTransportId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "WIND_SPEED_EXTRA_FEE_TRANSPORT")
public class WindSpeedExtraFeeTransport {
    @EmbeddedId
    private WindSpeedExtraFeeTransportId id;

    @MapsId("windSpeedId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "WIND_SPEED_ID", nullable = false)
    private WindSpeedExtraFee windSpeed;

    @MapsId("transportId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "TRANSPORT_ID", nullable = false)
    private TransportEntity transport;


}