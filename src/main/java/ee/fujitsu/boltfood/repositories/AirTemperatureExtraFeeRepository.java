package ee.fujitsu.boltfood.repositories;

import ee.fujitsu.boltfood.entities.airTemperature.AirTemperatureExtraFeeEntity;
import ee.fujitsu.boltfood.repositories.projections.ExtraFeeOrIsForbidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface AirTemperatureExtraFeeRepository extends JpaRepository<AirTemperatureExtraFeeEntity, Integer> {
    @Query("""
        SELECT a_t.extraFee AS extraFee, a_t.isForbidden AS isForbidden
        FROM AirTemperatureExtraFeeEntity a_t
        INNER JOIN AirTemperatureExtraFeeTransport a_t_transport ON a_t.id = a_t_transport.id.airTemperatureId
        WHERE (a_t.lowerBound IS NULL OR a_t.lowerBound <= :airTemperature) AND (a_t.higherBound IS NULL OR :airTemperature < a_t.higherBound)
        AND a_t_transport.id.transportId = (SELECT t_e.id FROM TransportEntity t_e WHERE LOWER(t_e.name) = LOWER(:transport))
        AND a_t.timestamp <= :timestamp
        ORDER BY a_t.timestamp DESC
        LIMIT 1
    """)
    Optional<ExtraFeeOrIsForbidden> getLatestAtTimestampAirTemperatureExtraFee(
            @Param("airTemperature") Float airTemperature,
            @Param("transport") String transport,
            @Param("timestamp") Instant timestamp);
}