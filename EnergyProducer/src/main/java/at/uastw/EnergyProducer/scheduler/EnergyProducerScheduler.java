package at.uastw.EnergyProducer.scheduler;

import at.uastw.EnergyProducer.model.ProducedEnergyMessage;
import at.uastw.EnergyProducer.service.messaging.ProducedEnergyMessageProducer;
import at.uastw.EnergyProducer.service.business.SolarEnergyGeneratorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EnergyProducerScheduler {
    private final SolarEnergyGeneratorService energyGenerator;
    private final ProducedEnergyMessageProducer messagePublisher;

    public EnergyProducerScheduler(SolarEnergyGeneratorService energyGenerator, ProducedEnergyMessageProducer messagePublisher) {
        this.energyGenerator = energyGenerator;
        this.messagePublisher = messagePublisher;
    }

    @Scheduled(fixedRate = 5000)
    public void generateEnergyANDsendProducedEnergyMessage() {
        double generatedEnergyInKwh = energyGenerator.generateEnergyInKwh();

        ProducedEnergyMessage producedEnergyMessage = new ProducedEnergyMessage();
        producedEnergyMessage.setType("PRODUCER");
        producedEnergyMessage.setAssociation("COMMUNITY");
        producedEnergyMessage.setAmountInKwh(generatedEnergyInKwh);
        producedEnergyMessage.setDatetime(LocalDateTime.now());

        messagePublisher.publishMessage(producedEnergyMessage);
    }
}
