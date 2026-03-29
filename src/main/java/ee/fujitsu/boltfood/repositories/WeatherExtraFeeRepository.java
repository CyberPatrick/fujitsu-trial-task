package ee.fujitsu.boltfood.repositories;

import ee.fujitsu.boltfood.entities.weatherPhenomenon.WeatherExtraFee;
import ee.fujitsu.boltfood.repositories.projections.ExtraFeeOrIsForbidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface WeatherExtraFeeRepository extends JpaRepository<WeatherExtraFee, Integer> {
    @Query("""
        SELECT w_ef.extraFee AS extraFee, w_ef.isForbidden AS isForbidden
        FROM WeatherExtraFee w_ef
        INNER JOIN WeatherExtraFeeTransport w_ef_transport ON w_ef.id = w_ef_transport.id.weatherId
        INNER JOIN WeatherPhenomenonExtraFeeCategory w_ef_category ON w_ef.id = w_ef_category.id.weatherPhenomenonExtraFeeId
        WHERE w_ef_transport.id.transportId = (SELECT t_e.id FROM TransportEntity t_e WHERE LOWER(t_e.name) = LOWER(:transport))
        AND w_ef_category.id.weatherCategoryId =
         (SELECT w_category.id FROM WeatherCategory w_category WHERE LOWER(w_category.name) = LOWER(:weatherPhenomenon))
        AND w_ef.timestamp <= :timestamp
        ORDER BY w_ef.timestamp DESC
        LIMIT 1
    """)
    Optional<ExtraFeeOrIsForbidden> getLatestAtTimestampWeatherPhenomenonExtraFee(
            @Param("weatherPhenomenon") String weatherPhenomenon,
            @Param("transport") String transport,
            @Param("timestamp") Instant timestamp);
}