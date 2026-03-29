package ee.fujitsu.boltfood.schedulers;

import ee.fujitsu.boltfood.services.WeatherDataImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherDataImportScheduler {
    private final WeatherDataImportService importService;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        try {
            importService.importWeatherData();
        } catch (Exception e) {
            log.error("Tried to import weather data on startup, exception occured", e);
        }
    }

    @Scheduled(cron = "${weather.import.cron}")
    public void runHourly() {
        importService.importWeatherData();
    }
}
