package at.uastw.EnergyUser.scheduler;

import at.uastw.EnergyUser.dto.UsedEnergyMessageDto;
import at.uastw.EnergyUser.service.messaging.UsedEnergyMessageProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class EnergyUserScheduler {

    private final UsedEnergyMessageProducer messageProducer;

    public EnergyUserScheduler(UsedEnergyMessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Scheduled(fixedRate = 5000)
    public void useEnergyANDsendUsedEnergyMessage() {
        double usedEnergyInKwh = generateUsedEnergyInKwh();

        UsedEnergyMessageDto message = new UsedEnergyMessageDto();

        message.setType("USER");
        message.setAssociation("COMMUNITY");
        message.setAmountInKwh(usedEnergyInKwh);
        message.setDatetime(LocalDateTime.now());

        messageProducer.publish(message);
    }

    private double generateUsedEnergyInKwh() {
        // dailykWh = random dailykWh per home * number of homes
        double dailyKwh = ThreadLocalRandom.current().nextDouble(8.0, 35.0) * 10;

        // intervalkWh = dailykWh * scheduler interval seconds / seconds per day
        double intervalKwh = dailyKwh * 5.0 / (24 * 60 * 60);

        // rounding to 3 decimal places
        return Math.round(intervalKwh * 1000.0) / 1000.0;
    }
}