package at.uastw.EnergyProducer.scheduler;

import at.uastw.EnergyProducer.dto.ProducedEnergyMessageDto;
import at.uastw.EnergyProducer.service.messaging.ProducedEnergyMessageProducer;
import at.uastw.EnergyProducer.service.business.SolarEnergyGeneratorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EnergyProducerScheduler {
    private final SolarEnergyGeneratorService energyGenerator;
    private final ProducedEnergyMessageProducer messageProducer;

    public EnergyProducerScheduler(SolarEnergyGeneratorService energyGenerator, ProducedEnergyMessageProducer messageProducer) {
        this.energyGenerator = energyGenerator;
        this.messageProducer = messageProducer;
    }

    @Scheduled(fixedRate = 5000)
    public void produceEnergyAndSendProducedEnergyMessage() {
        double producedEnergyInKwh = energyGenerator.produceEnergyInKwh();

        ProducedEnergyMessageDto message = new ProducedEnergyMessageDto(
                "PRODUCER",
                "COMMUNITY",
                producedEnergyInKwh,
                LocalDateTime.now()
        );

        messageProducer.publish(message);
    }
}
