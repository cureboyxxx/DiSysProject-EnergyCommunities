package at.uastw.EnergyProducer.service.impl;

import at.uastw.EnergyProducer.model.WeatherCondition;
import at.uastw.EnergyProducer.model.WeatherResponse;
import at.uastw.EnergyProducer.service.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenMeteoWeatherService implements WeatherService {
    private final RestClient restClient;
    private final String openMeteoApiURL = "https://api.open-meteo.com/v1/forecast?latitude=48.2085&longitude=16.3721&current=is_day,cloud_cover";

    public OpenMeteoWeatherService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    @Override
    public WeatherCondition getCurrentWeatherCondition() {
        WeatherResponse response = restClient.get()
                .uri(openMeteoApiURL)
                .retrieve()
                .body(WeatherResponse.class);

        return new WeatherCondition(
                response.getCurrent().getIsDay() == 1,
                response.getCurrent().getCloudCover()
        );
    }
}
