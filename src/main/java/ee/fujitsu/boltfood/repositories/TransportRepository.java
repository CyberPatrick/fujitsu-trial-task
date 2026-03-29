package ee.fujitsu.boltfood.repositories;

import ee.fujitsu.boltfood.entities.TransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRepository extends JpaRepository<TransportEntity, Integer> {
    boolean existsByNameEqualsIgnoreCase(String transportType);
}