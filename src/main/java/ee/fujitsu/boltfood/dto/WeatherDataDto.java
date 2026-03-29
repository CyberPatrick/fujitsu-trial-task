package ee.fujitsu.boltfood.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Objects;

public record WeatherDataDto(@NotNull String stationName,
                             Integer wmoCode,
                             String weatherPhenomenon,
                             Float airTemperature,
                             Float windSpeed,
                             @NotNull Instant timestamp) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WeatherDataDto that = (WeatherDataDto) o;
        return Objects.equals(wmoCode, that.wmoCode) && Objects.equals(timestamp, that.timestamp) && Objects.equals(stationName, that.stationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationName, wmoCode, timestamp);
    }
}
