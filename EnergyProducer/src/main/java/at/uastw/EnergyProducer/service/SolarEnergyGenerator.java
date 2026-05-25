package at.uastw.EnergyProducer.service;

import at.uastw.EnergyProducer.model.WeatherCondition;
import org.springframework.stereotype.Service;

import java.util.Random;

// This generates mock data based on the info from the
// weatherAPI for a typical solar panel energy source

// This is how I understand the requirements
// in the project specification - Mario

// In theory, we could also add a mock wind turbine, etc.
// but I do not think that adds anything to the project
// because this would generate the same mock data
// based on slightly different variables

@Service
public class SolarEnergyGenerator {
    private final OpenMeteoWeatherService weatherService;
    private final Random random = new Random();

    public SolarEnergyGenerator(OpenMeteoWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public double generateEnergyInKwh() {
        WeatherCondition weatherCondition = weatherService.getCurrentWeatherCondition();

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
