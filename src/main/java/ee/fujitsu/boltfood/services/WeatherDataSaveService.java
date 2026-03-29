package ee.fujitsu.boltfood.services;

import ee.fujitsu.boltfood.entities.WeatherDataEntity;
import ee.fujitsu.boltfood.repositories.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
class WeatherDataSaveService {
    private final WeatherDataRepository repository;

    /**
     * Persists the given {@link WeatherDataEntity} instance to the database and immediately flushes the changes.
     * This method is executed within a new transactional context.
     * It is necessary mostly for saving a lot of entities independently.
     *
     * @param entity the {@link WeatherDataEntity} instance to be saved and flushed to the database
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAndFlush(WeatherDataEntity entity) {
        repository.saveAndFlush(entity);
    }

}
