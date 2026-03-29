package ee.fujitsu.boltfood.schedulers;

import ee.fujitsu.boltfood.services.WeatherDataImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherDataImportScheduler {
    private final WeatherDataImportService importService;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        try {
            importService.importWeatherData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Scheduled(cron = "${weather.import.cron}")
    public void runHourly() {
        importService.importWeatherData();
    }
}
