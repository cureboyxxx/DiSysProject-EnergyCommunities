package at.uastw.EnergyUser.scheduler;

import at.uastw.EnergyUser.model.UsedEnergyMessage;
import at.uastw.EnergyUser.service.UsedEnergyRabbitMqPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EnergyUserScheduler {

    }

    @Scheduled(fixedRate = 5000)
    public void useEnergyANDsendUsedEnergyMessage() {
        double usedEnergyInKwh = 100;

        UsedEnergyMessage producedEnergyMessage = new UsedEnergyMessage();
        producedEnergyMessage.setType("USER");
        producedEnergyMessage.setAssociation("COMMUNITY");
        producedEnergyMessage.setAmountInKwh(usedEnergyInKwh);
        producedEnergyMessage.setDatetime(LocalDateTime.now().toString());

        messagePublisher.publishMessage(producedEnergyMessage);
    }
}

