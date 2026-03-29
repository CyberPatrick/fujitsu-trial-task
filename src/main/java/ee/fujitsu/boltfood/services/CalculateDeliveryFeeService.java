package ee.fujitsu.boltfood.services;

import ee.fujitsu.boltfood.dto.requests.calculatorController.DeliveryFeeRequest;
import ee.fujitsu.boltfood.entities.WeatherDataEntity;
import ee.fujitsu.boltfood.exceptions.InternalServerException;
import ee.fujitsu.boltfood.exceptions.calculatorExceptions.InvalidCityException;
import ee.fujitsu.boltfood.exceptions.calculatorExceptions.InvalidTransportException;
import ee.fujitsu.boltfood.exceptions.calculatorExceptions.NoDataAtSpecifiedDate;
import ee.fujitsu.boltfood.exceptions.calculatorExceptions.VehicleTypeIsForbiddenException;
import ee.fujitsu.boltfood.repositories.*;
import ee.fujitsu.boltfood.repositories.projections.ExtraFeeOrIsForbidden;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@AllArgsConstructor
@Service
public class CalculateDeliveryFeeService {
    private final WeatherDataRepository weatherDataRepository;
    private final BaseFeeRepository baseFeeEntityRepository;
    private final AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository;
    private final WindSpeedExtraFeeRepository windSpeedExtraFeeRepository;
    private final WeatherExtraFeeRepository weatherExtraFeeRepository;

    // Only for validating
    private final StationCityConnectionRepository stationCityConnectionRepository;
    private final TransportRepository transportRepository;

    public Float calculateDeliveryFee(DeliveryFeeRequest request) {
        validateRequestData(request);

        boolean isClientSpecifiedDate = Objects.nonNull(request.timestamp());
        Instant atWhichTimestamp = isClientSpecifiedDate ? request.timestamp() : Instant.now();
        Supplier<? extends RuntimeException> supplier =
                isClientSpecifiedDate
                        ? NoDataAtSpecifiedDate::new
                        : () -> new InternalServerException("No weather data");

        WeatherDataEntity weatherData = weatherDataRepository.getLatestAtTimestampWeatherDataByCity(
                request.city(), atWhichTimestamp)
                .orElseThrow(supplier);

        var baseFee = baseFeeEntityRepository.getLatestBaseFeeAtTimestamp(
                request.city(), request.transportType(), atWhichTimestamp)
                .orElseThrow(supplier)
                .getRegionalBaseFee();

        baseFee += getExtraFee(airTemperatureExtraFeeRepository.getLatestAtTimestampAirTemperatureExtraFee(
                weatherData.getAirTemperature(), request.transportType(), atWhichTimestamp));

        baseFee += getExtraFee(windSpeedExtraFeeRepository.getLatestAtTimestampWindSpeedExtraFee(
                weatherData.getWindSpeed(), request.transportType(), atWhichTimestamp));

        baseFee += getExtraFee(weatherExtraFeeRepository.getLatestAtTimestampWeatherPhenomenonExtraFee(
                weatherData.getWeatherPhenomenon(), request.transportType(), atWhichTimestamp));

        return baseFee;
    }

    private Float getExtraFee(Optional<ExtraFeeOrIsForbidden> extraFee) throws VehicleTypeIsForbiddenException {
        if (extraFee.isEmpty()) return 0f;
        if (extraFee.get().getIsForbidden()) throw new VehicleTypeIsForbiddenException();
        return extraFee.get().getExtraFee();
    }

    private void validateRequestData(DeliveryFeeRequest request) {
        if (!stationCityConnectionRepository.existsById_CityNameEqualsIgnoreCase(request.city()))
            throw new InvalidCityException();
        if (!transportRepository.existsByNameEqualsIgnoreCase(request.transportType()))
            throw new InvalidTransportException();
    }
}
