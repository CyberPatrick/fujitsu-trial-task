package ee.fujitsu.boltfood.services;

import ee.fujitsu.boltfood.dto.requests.calculatorController.DeliveryFeeRequest;
import ee.fujitsu.boltfood.entities.WeatherDataEntity;
import ee.fujitsu.boltfood.exceptions.calculatorExceptions.InvalidCityException;
import ee.fujitsu.boltfood.exceptions.calculatorExceptions.InvalidTransportException;
import ee.fujitsu.boltfood.exceptions.calculatorExceptions.NoDataAtSpecifiedDate;
import ee.fujitsu.boltfood.exceptions.calculatorExceptions.VehicleTypeIsForbiddenException;
import ee.fujitsu.boltfood.repositories.*;
import ee.fujitsu.boltfood.repositories.projections.ExtraFeeOrIsForbidden;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class CalculateDeliveryFeeServiceTest {

    @Autowired
    private CalculateDeliveryFeeService calculateDeliveryFeeService;

    @MockitoBean
    private WeatherDataRepository weatherDataRepository;

    @MockitoBean
    private BaseFeeRepository baseFeeEntityRepository;

    @MockitoBean
    private AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository;

    @MockitoBean
    private WindSpeedExtraFeeRepository windSpeedExtraFeeRepository;

    @MockitoBean
    private WeatherExtraFeeRepository weatherExtraFeeRepository;

    @MockitoBean
    private StationCityConnectionRepository stationCityConnectionRepository;

    @MockitoBean
    private TransportRepository transportRepository;

    @Test
    void calculateDeliveryFee_ValidData_ReturnsFee() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("Tallinn", "car", Instant.now());

        WeatherDataEntity weatherData = new WeatherDataEntity();
        weatherData.setAirTemperature(5.0f);
        weatherData.setWindSpeed(10.0f);
        weatherData.setWeatherPhenomenon("Clear");

        Mockito.when(stationCityConnectionRepository.existsById_CityNameEqualsIgnoreCase("Tallinn"))
                .thenReturn(true);
        Mockito.when(transportRepository.existsByNameEqualsIgnoreCase("car"))
                .thenReturn(true);
        Mockito.when(weatherDataRepository.getLatestAtTimestampWeatherDataByCity(eq("Tallinn"), any(Instant.class)))
                .thenReturn(Optional.of(weatherData));
        Mockito.when(baseFeeEntityRepository.getLatestBaseFeeAtTimestamp(eq("Tallinn"), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(() -> 5.0f));
        Mockito.when(airTemperatureExtraFeeRepository.getLatestAtTimestampAirTemperatureExtraFee(eq(5.0f), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(extraFee(1.0f)));
        Mockito.when(windSpeedExtraFeeRepository.getLatestAtTimestampWindSpeedExtraFee(eq(10.0f), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(extraFee(0.5f)));
        Mockito.when(weatherExtraFeeRepository.getLatestAtTimestampWeatherPhenomenonExtraFee(eq("Clear"), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(extraFee(2.0f)));

        Float fee = calculateDeliveryFeeService.calculateDeliveryFee(request);

        assertEquals(8.5f, fee);
    }

    @Test
    void calculateDeliveryFee_InvalidCity_ThrowsError() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("InvalidCity", "car", Instant.now());

        Mockito.when(stationCityConnectionRepository.existsById_CityNameEqualsIgnoreCase("InvalidCity"))
                .thenReturn(false);

        assertThrows(InvalidCityException.class, () -> calculateDeliveryFeeService.calculateDeliveryFee(request));
    }

    @Test
    void calculateDeliveryFee_InvalidTransport_ThrowsError() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("Tallinn", "invalidTransport", Instant.now());

        Mockito.when(stationCityConnectionRepository.existsById_CityNameEqualsIgnoreCase("Tallinn"))
                .thenReturn(true);
        Mockito.when(transportRepository.existsByNameEqualsIgnoreCase("invalidTransport"))
                .thenReturn(false);

        assertThrows(InvalidTransportException.class, () -> calculateDeliveryFeeService.calculateDeliveryFee(request));
    }

    @Test
    void calculateDeliveryFee_NoWeatherDataAtTimestamp_ThrowsError() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("Tallinn", "car", Instant.now());

        Mockito.when(stationCityConnectionRepository.existsById_CityNameEqualsIgnoreCase("Tallinn"))
                .thenReturn(true);
        Mockito.when(transportRepository.existsByNameEqualsIgnoreCase("car"))
                .thenReturn(true);
        Mockito.when(weatherDataRepository.getLatestAtTimestampWeatherDataByCity(eq("Tallinn"), any(Instant.class)))
                .thenReturn(Optional.empty());

        assertThrows(NoDataAtSpecifiedDate.class, () -> calculateDeliveryFeeService.calculateDeliveryFee(request));
    }

    @Test
    void calculateDeliveryFee_ForbiddenVehicle_ThrowsError() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("Tallinn", "car", Instant.now());

        WeatherDataEntity weatherData = new WeatherDataEntity();
        weatherData.setAirTemperature(5.0f);

        Mockito.when(stationCityConnectionRepository.existsById_CityNameEqualsIgnoreCase("Tallinn"))
                .thenReturn(true);
        Mockito.when(transportRepository.existsByNameEqualsIgnoreCase("car"))
                .thenReturn(true);
        Mockito.when(weatherDataRepository.getLatestAtTimestampWeatherDataByCity(eq("Tallinn"), any(Instant.class)))
                .thenReturn(Optional.of(weatherData));
        Mockito.when(baseFeeEntityRepository.getLatestBaseFeeAtTimestamp(eq("Tallinn"), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(() -> 5.0f));
        Mockito.when(airTemperatureExtraFeeRepository.getLatestAtTimestampAirTemperatureExtraFee(eq(5.0f), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(forbidden()));

        assertThrows(VehicleTypeIsForbiddenException.class, () -> calculateDeliveryFeeService.calculateDeliveryFee(request));
    }

    @Test
    void calculateDeliveryFee_NoTimestamp_UsesCurrentTime() {
        DeliveryFeeRequest request = new DeliveryFeeRequest("Tallinn", "car", null);

        WeatherDataEntity weatherData = new WeatherDataEntity();
        weatherData.setAirTemperature(5.0f);
        weatherData.setWindSpeed(10.0f);
        weatherData.setWeatherPhenomenon("Rain");

        Mockito.when(stationCityConnectionRepository.existsById_CityNameEqualsIgnoreCase("Tallinn"))
                .thenReturn(true);
        Mockito.when(transportRepository.existsByNameEqualsIgnoreCase("car"))
                .thenReturn(true);
        Mockito.when(weatherDataRepository.getLatestAtTimestampWeatherDataByCity(eq("Tallinn"), any(Instant.class)))
                .thenReturn(Optional.of(weatherData));
        Mockito.when(baseFeeEntityRepository.getLatestBaseFeeAtTimestamp(eq("Tallinn"), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(() -> 6.0f));
        Mockito.when(airTemperatureExtraFeeRepository.getLatestAtTimestampAirTemperatureExtraFee(eq(5.0f), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(extraFee(1.0f)));
        Mockito.when(windSpeedExtraFeeRepository.getLatestAtTimestampWindSpeedExtraFee(eq(10.0f), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(extraFee(0.5f)));
        Mockito.when(weatherExtraFeeRepository.getLatestAtTimestampWeatherPhenomenonExtraFee(eq("Rain"), eq("car"), any(Instant.class)))
                .thenReturn(Optional.of(extraFee(2.0f)));

        Float fee = calculateDeliveryFeeService.calculateDeliveryFee(request);

        assertEquals(9.5f, fee);
    }

    private ExtraFeeOrIsForbidden extraFee(float fee) {
        return new ExtraFeeOrIsForbidden() {
            @Override public Float getExtraFee() { return fee; }
            @Override public Boolean getIsForbidden() { return false; }
        };
    }

    private ExtraFeeOrIsForbidden forbidden() {
        return new ExtraFeeOrIsForbidden() {
            @Override public Float getExtraFee() { return null; }
            @Override public Boolean getIsForbidden() { return true; }
        };
    }
}