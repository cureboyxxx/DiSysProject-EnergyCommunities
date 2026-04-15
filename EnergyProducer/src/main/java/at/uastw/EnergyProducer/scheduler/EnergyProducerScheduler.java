package at.uastw.EnergyProducer.scheduler;

import at.uastw.EnergyProducer.model.ProducedEnergyMessage;
import at.uastw.EnergyProducer.service.EnergyGenerator;
import at.uastw.EnergyProducer.service.MessagePublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class EnergyProducerScheduler {
    private EnergyGenerator energyGenerator;
    private MessagePublisher messagePublisher;

    public EnergyProducerScheduler(EnergyGenerator energyGenerator, MessagePublisher messagePublisher) {
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
