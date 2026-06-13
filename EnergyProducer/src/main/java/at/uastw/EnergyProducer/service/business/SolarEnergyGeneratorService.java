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
        double schedulerIntervalInSeconds = 5.0;
        int secondsPerDay = 24 * 60 * 60;

        double minimumDailyKwh = 10.0;
        double maximumDailyKwh = 15.0;

        double efficiencyLossPerCloudCoverPercentagePoint = 0.008;
        double minimumEnergyMultiplier = 0.85;
        double randomEnergyMultiplierRange = 0.30;

        WeatherConditionDto weatherCondition = weatherService.getCurrentWeatherCondition();

        if (!weatherCondition.isDay()) {
            return 0.0;
        }

        double randomDailyKwh = random.nextDouble(minimumDailyKwh, maximumDailyKwh);
        double intervalKwh = randomDailyKwh * schedulerIntervalInSeconds / secondsPerDay;

        double cloudCover = weatherCondition.getCloudCover();
        double cloudCoverAdjustedEfficiency = 1.0 - (cloudCover * efficiencyLossPerCloudCoverPercentagePoint);
        double energyMultiplier = minimumEnergyMultiplier + (random.nextDouble() * randomEnergyMultiplierRange);

        double producedEnergyInKwh = intervalKwh * cloudCoverAdjustedEfficiency * energyMultiplier;

        // rounding to 3 decimal places
        return Math.round(producedEnergyInKwh * 1000.0) / 1000.0;
    }
}
