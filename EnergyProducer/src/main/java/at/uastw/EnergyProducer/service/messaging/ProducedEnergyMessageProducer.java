package at.uastw.EnergyProducer.service.messaging;

import at.uastw.EnergyProducer.dto.ProducedEnergyMessageDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
                    "produced_energy",
                    payload,
                    this::setJsonContentType
            );

        } catch (Exception ex) {
            throw new IllegalStateException("Could not publish produced_energy message", ex);
        }
    }

    private Message setJsonContentType(Message rabbitMessage) {
        rabbitMessage.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return rabbitMessage;
    }
}
