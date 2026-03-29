package ee.fujitsu.boltfood.entities.stationCityConnection;

import ee.fujitsu.boltfood.entities.stationCityConnection.primaryKey.StationCityConnectionId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "STATION_CITY_CONNECTION")
public class StationCityConnection {
    @EmbeddedId
    private StationCityConnectionId id;
}