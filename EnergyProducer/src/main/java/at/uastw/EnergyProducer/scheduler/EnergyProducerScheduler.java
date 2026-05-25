package at.uastw.EnergyProducer.scheduler;

import at.uastw.EnergyProducer.model.ProducedEnergyMessage;
import at.uastw.EnergyProducer.service.ProducedEnergyRabbitMqPublisher;
import at.uastw.EnergyProducer.service.SolarEnergyGenerator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EnergyProducerScheduler {
    private final SolarEnergyGenerator energyGenerator;
    private final ProducedEnergyRabbitMqPublisher messagePublisher;

    public EnergyProducerScheduler(SolarEnergyGenerator energyGenerator, ProducedEnergyRabbitMqPublisher messagePublisher) {
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
        producedEnergyMessage.setDatetime(LocalDateTime.now().toString());

        messagePublisher.publishMessage(producedEnergyMessage);
    }
}
