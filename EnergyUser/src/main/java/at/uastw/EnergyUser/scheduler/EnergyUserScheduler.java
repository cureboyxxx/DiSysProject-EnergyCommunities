package at.uastw.EnergyUser.scheduler;

import at.uastw.EnergyUser.dto.UsedEnergyMessageDto;
import at.uastw.EnergyUser.service.messaging.UsedEnergyMessageProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class EnergyUserScheduler {
    private final UsedEnergyMessageProducer messageProducer;
    private final Random random = new Random();

    public EnergyUserScheduler(UsedEnergyMessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Scheduled(fixedRate = 5000)
    public void useEnergyAndSendUsedEnergyMessage() {
        double usedEnergyInKwh = calculateUsedEnergyInKwh();

        UsedEnergyMessageDto message = new UsedEnergyMessageDto(
                "USER",
                "COMMUNITY",
                usedEnergyInKwh,
                LocalDateTime.now()
        );

        messageProducer.publish(message);
    }

    private double calculateUsedEnergyInKwh() {
        double schedulerIntervalInSeconds = 5.0;
        int secondsPerDay = 24 * 60 * 60;

        double minimumDailyKwhPerHome = 8.0;
        double maximumDailyKwhPerHome = 35.0;
        int numberOfHomes = 10;

        double randomDailyKwhPerHome = random.nextDouble(minimumDailyKwhPerHome, maximumDailyKwhPerHome);

        double dailyKwh = randomDailyKwhPerHome * numberOfHomes;

        double intervalKwh = dailyKwh * schedulerIntervalInSeconds / secondsPerDay;

        // rounding to 3 decimal places
        return Math.round(intervalKwh * 1000.0) / 1000.0;
    }
}
