package at.uastw.EnergyProducer.service.messaging;

import at.uastw.EnergyProducer.dto.ProducedEnergyMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class ProducedEnergyMessageProducer {

    private final RabbitTemplate rabbit;
    private final ObjectMapper objectMapper;

    public ProducedEnergyMessageProducer(RabbitTemplate rabbit, ObjectMapper objectMapper) {
        this.rabbit = rabbit;
        this.objectMapper = objectMapper;
    }

    public void publish(ProducedEnergyMessageDto message) {
        try {
            String payload = objectMapper.writeValueAsString(message);

            rabbit.convertAndSend(
                    "energy_message",
                    payload
            );

        } catch (Exception ex) {
            throw new IllegalStateException("Could not publish message", ex);
        }
    }
}
