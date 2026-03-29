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
    @Value("${weather.api.url}")
    private String API_URL;

    /**
     * Imports weather data by downloading it from a remote API, parsing the retrieved XML data,
     * transforming it into domain entities, and saving it to the database.
     * <ul>
     * <li>Retrieves weather data XML using a REST client from the configured API URL.</li>
     * <li>Parses the XML response into a {@link ObservationsApiResponse} object.</li>
     * <li>Maps the parsed response into a set of {@link WeatherDataDto} objects.</li>
     * <li>Converts the DTOs into {@link WeatherDataEntity} objects for persistence.</li>
     * <li>Saves each {@link WeatherDataEntity} to the database, handling possible data conflicts
     *   (e.g., duplicate entries) or unknown exceptions during persistence.</li>
     * </ul>
     * Logs processing steps including data retrieval, parsing, mapping, and saving operations.
     * Skips duplicate entities during the save operation and logs warnings for them.
     * Logs errors when unknown exceptions occur while saving entities.
     */
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
