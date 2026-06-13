package at.uastw.EnergyUser.scheduler;

import at.uastw.EnergyUser.dto.UsedEnergyMessageDto;
import at.uastw.EnergyUser.service.messaging.UsedEnergyMessageProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class EnergyUserScheduler {
    private final UsedEnergyMessageProducer messageProducer;
    private LocalDateTime lastMessageTime = LocalDateTime.now();
    private LocalDateTime nextMessageTime = LocalDateTime.now();

    public EnergyUserScheduler(UsedEnergyMessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Scheduled(fixedDelay = 1000)
    public void useEnergyAndSendUsedEnergyMessage() {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(nextMessageTime)) {
            return;
        }

        double usedEnergyInKwh = calculateUsedEnergyInKwh(now);

        UsedEnergyMessageDto message = new UsedEnergyMessageDto(
                "USER",
                "COMMUNITY",
                usedEnergyInKwh,
                now
        );

        messageProducer.publish(message);

        lastMessageTime = now;
        nextMessageTime = now.plusSeconds(calculateSecondsUntilNextMessage());
    }

    private double calculateUsedEnergyInKwh(LocalDateTime now) {
        double secondsSinceLastMessage = calculateSecondsSinceLastMessage(now);
        double usedPowerInKw = calculateUsedEnergy(now);

        return usedPowerInKw * secondsSinceLastMessage / 3600.0;
    }

    private double calculateSecondsSinceLastMessage(LocalDateTime now) {
        return Duration.between(lastMessageTime, now).toMillis() / 1000.0;
    }

    private int calculateSecondsUntilNextMessage() {
        return ThreadLocalRandom.current().nextInt(1, 6);
    }

    private double calculateUsedEnergy(LocalDateTime now) {
        double minimumEnergyInKw;
        double maximumEnergyInKw;
        int hour = now.getHour();

        if (hour >= 0 && hour < 5) {
            minimumEnergyInKw = 0.15;
            maximumEnergyInKw = 0.35;

        } else if (hour >= 5 && hour < 9) {
            minimumEnergyInKw = 0.70;
            maximumEnergyInKw = 2.20;

        } else if (hour >= 9 && hour < 16) {
            minimumEnergyInKw = 0.25;
            maximumEnergyInKw = 0.80;

        } else if (hour >= 16 && hour < 21) {
            minimumEnergyInKw = 1.00;
            maximumEnergyInKw = 3.00;

        } else {
            minimumEnergyInKw = 0.40;
            maximumEnergyInKw = 1.00;
        }

        return ThreadLocalRandom.current().nextDouble(minimumEnergyInKw, maximumEnergyInKw);
    }
}
