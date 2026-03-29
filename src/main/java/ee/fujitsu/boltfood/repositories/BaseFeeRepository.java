package ee.fujitsu.boltfood.repositories;

import ee.fujitsu.boltfood.entities.BaseFeeEntity;
import ee.fujitsu.boltfood.repositories.projections.RegionalBaseFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface BaseFeeRepository extends JpaRepository<BaseFeeEntity, Long> {
    @Query("""
        SELECT b_f.regionalBaseFee as regionalBaseFee
        FROM BaseFeeEntity b_f
        INNER JOIN b_f.transport
        WHERE LOWER(b_f.transport.name) = LOWER(:transport) AND LOWER(b_f.city) = LOWER(:city)
        AND b_f.timestamp <= :timestamp
        ORDER BY b_f.timestamp DESC
        LIMIT 1
    """)
    Optional<RegionalBaseFee> getLatestBaseFeeAtTimestamp(
            @Param("city") String city,
            @Param("transport") String transport,
            @Param("timestamp") Instant timestamp);

    // Same as above, in upper version function name is shorter
    //Optional<RegionalBaseFee> findFirstByCityIgnoreCaseAndTransport_NameAndTimestampLessThanEqualOrderByTimestampDesc(String city, String transport, Instant timestamp);
}