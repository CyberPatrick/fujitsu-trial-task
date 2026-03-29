package ee.fujitsu.boltfood.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ee.fujitsu.boltfood.dto.ObservationsApiResponse;
import ee.fujitsu.boltfood.dto.WeatherDataDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDataParser {
    private final XmlMapper xmlMapper = new XmlMapper();

    /**
     * Parses the provided XML string containing weather data into an {@link ObservationsApiResponse} object.
     *
     * @param weatherDataXml the XML string representing weather data
     * @return an {@link ObservationsApiResponse} object containing the parsed weather data
     * @throws RuntimeException if the XML cannot be parsed into an {@link ObservationsApiResponse}
     */
    public ObservationsApiResponse parse(String weatherDataXml) {
        try {
            return xmlMapper.readValue(weatherDataXml, ObservationsApiResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather data: " + weatherDataXml, e);
        }
    }
}
