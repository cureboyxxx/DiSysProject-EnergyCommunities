package at.uastw.EnergyProducer.scheduler;

import at.uastw.EnergyProducer.dto.ProducedEnergyMessageDto;
import at.uastw.EnergyProducer.service.messaging.ProducedEnergyMessageProducer;
import at.uastw.EnergyProducer.service.business.SolarEnergyGeneratorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class EnergyProducerScheduler {
    private final SolarEnergyGeneratorService energyGenerator;
    private final ProducedEnergyMessageProducer messageProducer;

    private LocalDateTime lastMessageTime = LocalDateTime.now();
    private LocalDateTime nextMessageTime = LocalDateTime.now();

    public EnergyProducerScheduler(SolarEnergyGeneratorService energyGenerator, ProducedEnergyMessageProducer messageProducer) {
        this.energyGenerator = energyGenerator;
        this.messageProducer = messageProducer;
    }

    @Scheduled(fixedDelay = 1000)
    public void produceEnergyAndSendProducedEnergyMessage() {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(nextMessageTime)) {
            return;
        }

        double producedEnergyInKwh = calculateProducedEnergyInKwh(now);

        ProducedEnergyMessageDto message = new ProducedEnergyMessageDto(
                "PRODUCER",
                "COMMUNITY",
                producedEnergyInKwh,
                now
        );

        messageProducer.publish(message);

        lastMessageTime = now;
        nextMessageTime = now.plusSeconds(calculateSecondsUntilNextMessage());
    }

    private double calculateSecondsSinceLastMessage(LocalDateTime now) {
        return Duration.between(lastMessageTime, now).toMillis() / 1000.0;
    }

    private int calculateSecondsUntilNextMessage() {
        return ThreadLocalRandom.current().nextInt(1, 6);
    }

    private double calculateProducedEnergyInKwh(LocalDateTime now) {
        double secondsSinceLastMessage = calculateSecondsSinceLastMessage(now);
        double producedEnergyInKwhPerSecond = energyGenerator.produceEnergyInKwh();

        return producedEnergyInKwhPerSecond * secondsSinceLastMessage;
    }
}
