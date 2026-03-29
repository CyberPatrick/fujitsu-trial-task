package ee.fujitsu.boltfood.repositories;

import ee.fujitsu.boltfood.entities.WeatherDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity, Long> {
    @Query("""
        SELECT w
        FROM WeatherDataEntity w
        WHERE LOWER(w.stationName) = (SELECT LOWER(c.id.stationName) FROM StationCityConnection c WHERE LOWER(c.id.cityName) = LOWER(:city))
        AND w.timestamp <= :timestamp
        ORDER BY w.timestamp DESC
        LIMIT 1
    """)
    Optional<WeatherDataEntity> getLatestAtTimestampWeatherDataByCity(@Param("city") String city,
                                                                      @Param("timestamp") Instant timestamp);
}