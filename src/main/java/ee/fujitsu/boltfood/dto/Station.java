package ee.fujitsu.boltfood.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Station(@JsonProperty("name") String stationName,
                             @JsonProperty("wmocode") Integer wmoCode,
                             @JsonProperty("phenomenon") String weatherPhenomenon,
                             @JsonProperty("airtemperature") Float airTemperature,
                             @JsonProperty("windspeed") Float windSpeed) {
}
