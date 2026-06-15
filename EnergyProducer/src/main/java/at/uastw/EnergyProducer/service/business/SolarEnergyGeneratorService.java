package at.uastw.EnergyProducer.service.business;

import at.uastw.EnergyProducer.dto.WeatherConditionDto;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class SolarEnergyGeneratorService {
    private final OpenMeteoWeatherService weatherService;

    public SolarEnergyGeneratorService(OpenMeteoWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public double produceEnergyInKwh() {
        double baseProducedEnergyInKwh = 0.00025;
        double efficiencyLossPerCloudCoverPercentagePoint = 0.008;

        double minimumEnergyMultiplier = 0.85;
        double randomEnergyMultiplierRange = 0.30;

        WeatherConditionDto weatherCondition = weatherService.getCurrentWeatherCondition();

        if (!weatherCondition.isDay()) {
            return 0.0;
        }

        double cloudCover = weatherCondition.getCloudCover();
        double cloudCoverAdjustedEfficiency = 1.0 - (cloudCover * efficiencyLossPerCloudCoverPercentagePoint);

        double energyMultiplier = minimumEnergyMultiplier + (ThreadLocalRandom.current().nextDouble() * randomEnergyMultiplierRange);

        return baseProducedEnergyInKwh * cloudCoverAdjustedEfficiency * energyMultiplier;
    }
}
