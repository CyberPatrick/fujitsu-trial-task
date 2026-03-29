package ee.fujitsu.boltfood.services;

import ee.fujitsu.boltfood.dto.ObservationsApiResponse;
import ee.fujitsu.boltfood.dto.WeatherDataDto;
import ee.fujitsu.boltfood.entities.WeatherDataEntity;
import ee.fujitsu.boltfood.mappers.WeatherDataMapper;
import ee.fujitsu.boltfood.repositories.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherDataImportService {
    private final RestClient restClient;
    private final WeatherDataParser weatherDataParser;
    private final WeatherDataMapper mapper;
    private final WeatherDataRepository repository;
    private final WeatherDataSaveService saveService;
    @Value("${weather.api.url}") // TODO source of observations should be mentioned somewhere
    private String API_URL;

    public void importWeatherData() {
        log.info("Starting downloading new weather data");
        String weatherDataXml = restClient.get()
                .uri(API_URL)
                .retrieve()
                .body(String.class);
        log.info("Parsing weather data xml");
        ObservationsApiResponse apiResponse = weatherDataParser.parse(weatherDataXml);
        Set<WeatherDataDto> weatherData = mapper.toDtos(apiResponse.stations(), Instant.ofEpochSecond(apiResponse.timestamp()));
        Set<WeatherDataEntity> entities = mapper.toEntities(weatherData);
        log.info("Saving new weather data");
        for (WeatherDataEntity entity : entities) {
            try {
                saveService.saveAndFlush(entity);
            } catch (DataIntegrityViolationException e) {
                log.warn("Duplicate entity skipped: {}", entity, e);
            } catch (Exception e) {
                log.error("Failed to save entity due to unknown exception: {}", entity, e);
            }
        }
        log.info("Finished saving data");
    }
}
