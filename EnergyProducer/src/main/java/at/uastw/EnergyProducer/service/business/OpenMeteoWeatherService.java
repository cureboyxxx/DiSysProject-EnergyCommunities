package at.uastw.EnergyProducer.service.business;

import at.uastw.EnergyProducer.dto.WeatherConditionDto;
import at.uastw.EnergyProducer.dto.WeatherResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenMeteoWeatherService {
    private final RestClient restClient;
    private final String openMeteoApiURL = "https://api.open-meteo.com/v1/forecast?latitude=48.2085&longitude=16.3721&current=is_day,cloud_cover";

    public OpenMeteoWeatherService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public WeatherConditionDto getCurrentWeatherCondition() {
        WeatherResponseDto response = restClient.get()
                .uri(openMeteoApiURL)
                .retrieve()
                .body(WeatherResponseDto.class);

        if (response == null || response.getCurrent() == null) {
            throw new IllegalStateException("Open-Meteo response did not include current weather");
        }

        return new WeatherConditionDto(
                response.getCurrent().getIsDay() == 1,
                response.getCurrent().getCloudCover()
        );
    }
}
