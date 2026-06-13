package at.uastw.EnergyProducer.service.messaging;

import at.uastw.EnergyProducer.dto.ProducedEnergyMessageDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducedEnergyMessageProducer {

    private final RabbitTemplate rabbit;

    public ProducedEnergyMessageProducer(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void publish(ProducedEnergyMessageDto message) {
        try {
            rabbit.convertAndSend(
                    "produced_energy",
                    message
            );

        } catch (Exception ex) {
            throw new IllegalStateException("Could not publish produced_energy message", ex);
        }
    }
}
