package ee.fujitsu.boltfood.mappers;

import ee.fujitsu.boltfood.dto.Station;
import ee.fujitsu.boltfood.dto.WeatherDataDto;
import ee.fujitsu.boltfood.entities.WeatherDataEntity;
import org.mapstruct.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeatherDataMapper {
    WeatherDataEntity toEntity(WeatherDataDto weatherDataDto);
    Set<WeatherDataEntity> toEntities(Set<WeatherDataDto> weatherDataList);

    WeatherDataDto toDto(WeatherDataEntity weatherDataEntity);
    @Mapping(target = "timestamp", expression = "java(timestamp)")
    WeatherDataDto toDto(Station station, @Context Instant timestamp);
    Set<WeatherDataDto> toDtos(List<Station> stationList, @Context Instant timestamp);
}