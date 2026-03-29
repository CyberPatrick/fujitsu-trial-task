package ee.fujitsu.boltfood.mappers;

import ee.fujitsu.boltfood.dto.Station;
import ee.fujitsu.boltfood.dto.WeatherDataDto;
import ee.fujitsu.boltfood.entities.WeatherDataEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class validates the functionality of the WeatherDataMapper's toDto method.
 * The toDto method is responsible for mapping a WeatherDataEntity object to a WeatherDataDto object.
 */
public class WeatherDataMapperTest {

    private final WeatherDataMapper mapper = Mappers.getMapper(WeatherDataMapper.class);

    @Test
    void toDto_shouldMapAllFieldsCorrectly() {
        WeatherDataEntity entity = new WeatherDataEntity();
        entity.setId(1L);
        entity.setStationName("Test Station");
        entity.setWmoCode(123);
        entity.setAirTemperature(15.5f);
        entity.setWindSpeed(3.5f);
        entity.setWeatherPhenomenon("Clear");
        entity.setTimestamp(Instant.now());

        WeatherDataDto dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getStationName(), dto.stationName());
        assertEquals(entity.getWmoCode(), dto.wmoCode());
        assertEquals(entity.getAirTemperature(), dto.airTemperature());
        assertEquals(entity.getWindSpeed(), dto.windSpeed());
        assertEquals(entity.getWeatherPhenomenon(), dto.weatherPhenomenon());
        assertEquals(entity.getTimestamp(), dto.timestamp());
    }

    @Test
    void toDto_shouldHandleNullValues() {
        WeatherDataEntity entity = new WeatherDataEntity();
        entity.setId(1L);
        entity.setStationName("Test Station");
        entity.setWmoCode(null);
        entity.setAirTemperature(null);
        entity.setWindSpeed(null);
        entity.setWeatherPhenomenon(null);
        entity.setTimestamp(Instant.now());

        WeatherDataDto dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getStationName(), dto.stationName());
        assertNull(dto.wmoCode());
        assertNull(dto.airTemperature());
        assertNull(dto.windSpeed());
        assertNull(dto.weatherPhenomenon());
        assertEquals(entity.getTimestamp(), dto.timestamp());
    }

    @Test
    void toEntity_shouldMapAllFieldsCorrectly() {
        Instant timestamp = Instant.now();
        WeatherDataDto dto = new WeatherDataDto("Test Station", 123, "Clear", 15.5f, 3.5f, timestamp);

        WeatherDataEntity entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.stationName(), entity.getStationName());
        assertEquals(dto.wmoCode(), entity.getWmoCode());
        assertEquals(dto.airTemperature(), entity.getAirTemperature());
        assertEquals(dto.windSpeed(), entity.getWindSpeed());
        assertEquals(dto.weatherPhenomenon(), entity.getWeatherPhenomenon());
        assertEquals(dto.timestamp(), entity.getTimestamp());
    }

    @Test
    void toEntities_shouldMapSetOfDtos() {
        Instant timestamp = Instant.now();
        WeatherDataDto dto1 = new WeatherDataDto("Station 1", 123, "Clear", 15.5f, 3.5f, timestamp);
        WeatherDataDto dto2 = new WeatherDataDto("Station 2", 456, "Cloudy", 10.0f, 5.0f, timestamp);
        Set<WeatherDataDto> dtos = Set.of(dto1, dto2);

        Set<WeatherDataEntity> entities = mapper.toEntities(dtos);

        assertNotNull(entities);
        assertEquals(2, entities.size());
    }

    @Test
    void toDto_withStation_shouldMapAllFieldsAndTimestamp() {
        Instant timestamp = Instant.now();
        Station station = new Station("Test Station", 123, "Clear", 15.5f, 3.5f);

        WeatherDataDto dto = mapper.toDto(station, timestamp);

        assertNotNull(dto);
        assertEquals(station.stationName(), dto.stationName());
        assertEquals(station.wmoCode(), dto.wmoCode());
        assertEquals(station.airTemperature(), dto.airTemperature());
        assertEquals(station.windSpeed(), dto.windSpeed());
        assertEquals(station.weatherPhenomenon(), dto.weatherPhenomenon());
        assertEquals(timestamp, dto.timestamp());
    }

    @Test
    void toDtos_shouldMapListOfStationsWithTimestamp() {
        Instant timestamp = Instant.now();
        Station station1 = new Station("Station 1", 123, "Clear", 15.5f, 3.5f);
        Station station2 = new Station("Station 2", 456, "Cloudy", 10.0f, 5.0f);
        List<Station> stations = List.of(station1, station2);

        Set<WeatherDataDto> dtos = mapper.toDtos(stations, timestamp);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        dtos.forEach(dto -> assertEquals(timestamp, dto.timestamp()));
    }
}