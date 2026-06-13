package at.uastw.EnergyProducer.service.business;

import at.uastw.EnergyProducer.dto.WeatherConditionDto;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SolarEnergyGeneratorService {
    private final OpenMeteoWeatherService weatherService;
    private final Random random = new Random();

    public SolarEnergyGeneratorService(OpenMeteoWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public double produceEnergyInKwh() {
        WeatherConditionDto weatherCondition = weatherService.getCurrentWeatherCondition();

        if (!weatherCondition.isDay()) {
            return 0.0;
        }

        double maxEnergyCapacityInKwh = 1.0;
        double cloudCover = Math.max(0, Math.min(100, weatherCondition.getCloudCover()));
        double cloudCoverPenalty = cloudCover * 0.008;
        double efficiency = 1.0 - cloudCoverPenalty;
        double fluctuation = 0.9 + (random.nextDouble() * 0.2);

        return maxEnergyCapacityInKwh * efficiency * fluctuation;
    }
}