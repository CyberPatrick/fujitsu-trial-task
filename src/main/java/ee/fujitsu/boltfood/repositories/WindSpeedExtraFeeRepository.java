package ee.fujitsu.boltfood.repositories;

import ee.fujitsu.boltfood.entities.windSpeed.WindSpeedExtraFee;
import ee.fujitsu.boltfood.repositories.projections.ExtraFeeOrIsForbidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface WindSpeedExtraFeeRepository extends JpaRepository<WindSpeedExtraFee, Integer> {
    @Query("""
    SELECT w_t.extraFee AS extraFee, w_t.isForbidden AS isForbidden
    FROM WindSpeedExtraFee w_t
    INNER JOIN WindSpeedExtraFeeTransport w_t_transport ON w_t.id = w_t_transport.id.windSpeedId
    WHERE (w_t.lowerBound IS NULL OR w_t.lowerBound <= :windSpeed) AND (w_t.higherBound IS NULL OR :windSpeed < w_t.higherBound)
    AND w_t_transport.id.transportId = (SELECT t_e.id FROM TransportEntity t_e WHERE LOWER(t_e.name) = LOWER(:transport))
    AND w_t.timestamp <= :timestamp
    ORDER BY w_t.timestamp DESC
    LIMIT 1
    """)
    Optional<ExtraFeeOrIsForbidden> getLatestAtTimestampWindSpeedExtraFee(@Param("windSpeed") Float windSpeed,
                                                                          @Param("transport") String transport,
                                                                          @Param("timestamp") Instant timestamp);
}