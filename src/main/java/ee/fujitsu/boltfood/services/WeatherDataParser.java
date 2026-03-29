package ee.fujitsu.boltfood.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ee.fujitsu.boltfood.dto.ObservationsApiResponse;
import ee.fujitsu.boltfood.dto.WeatherDataDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDataParser {
    private final XmlMapper xmlMapper = new XmlMapper();

    public ObservationsApiResponse parse(String weatherDataXml) {
        try {
            return xmlMapper.readValue(weatherDataXml, ObservationsApiResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather data: " + weatherDataXml, e);
        }
    }
}
