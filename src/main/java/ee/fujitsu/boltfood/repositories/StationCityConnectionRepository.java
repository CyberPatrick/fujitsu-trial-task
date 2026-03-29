package ee.fujitsu.boltfood.repositories;

import ee.fujitsu.boltfood.entities.stationCityConnection.StationCityConnection;
import ee.fujitsu.boltfood.entities.stationCityConnection.primaryKey.StationCityConnectionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationCityConnectionRepository extends JpaRepository<StationCityConnection, StationCityConnectionId> {
    boolean existsById_CityNameEqualsIgnoreCase(String cityName);
}