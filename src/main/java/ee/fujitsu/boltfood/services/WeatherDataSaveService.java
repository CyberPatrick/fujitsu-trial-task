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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAndFlush(WeatherDataEntity entity) {
        repository.saveAndFlush(entity);
    }

}
