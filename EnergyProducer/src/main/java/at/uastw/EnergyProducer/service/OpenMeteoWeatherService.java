package at.uastw.EnergyProducer.service;

import at.uastw.EnergyProducer.model.WeatherCondition;
import at.uastw.EnergyProducer.model.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenMeteoWeatherService {
    private final RestClient restClient;
    private final String openMeteoApiURL = "https://api.open-meteo.com/v1/forecast?latitude=48.2085&longitude=16.3721&current=is_day,cloud_cover";

    public OpenMeteoWeatherService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public WeatherCondition getCurrentWeatherCondition() {
        WeatherResponse response = restClient.get()
                .uri(openMeteoApiURL)
                .retrieve()
                .body(WeatherResponse.class);

        if (response == null || response.getCurrent() == null) {
            throw new IllegalStateException("Open-Meteo response did not include current weather");
        }

        // TODO changed to isDay == 0 because I need data while working at night
        return new WeatherCondition(
                response.getCurrent().getIsDay() == 0,
                response.getCurrent().getCloudCover()
        );
    }
}
