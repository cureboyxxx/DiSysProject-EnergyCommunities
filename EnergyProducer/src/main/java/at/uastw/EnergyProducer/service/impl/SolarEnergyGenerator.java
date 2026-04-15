package at.uastw.EnergyProducer.service.impl;

import at.uastw.EnergyProducer.model.WeatherCondition;
import at.uastw.EnergyProducer.service.EnergyGenerator;
import at.uastw.EnergyProducer.service.WeatherService;
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
public class SolarEnergyGenerator implements EnergyGenerator {
    private final WeatherService weatherService;
    private final Random random = new Random();

    public SolarEnergyGenerator(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public double generateEnergyInKwh() {
        WeatherCondition weatherCondition = weatherService.getCurrentWeatherCondition();

        if (!weatherCondition.isDay()) {
            return 0.0;
        }

        double maxEnergyCapacityInKwh = 1.0;
        double cloudCoverPenalty = weatherCondition.getCloudCover() * 0.008;
        double efficiency = 1.0 - cloudCoverPenalty;
        double fluctuation = 0.9 + (random.nextDouble() * 0.2);

        return maxEnergyCapacityInKwh * efficiency * fluctuation;
    }
}
