package at.uastw.EnergyUser.service.messaging;

import at.uastw.EnergyUser.dto.UsedEnergyMessageDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class UsedEnergyMessageProducer {

    private final RabbitTemplate rabbit;
    private final ObjectMapper objectMapper;

    public UsedEnergyMessageProducer(RabbitTemplate rabbit, ObjectMapper objectMapper) {
        this.rabbit = rabbit;
        this.objectMapper = objectMapper;
    }

    public void publish(UsedEnergyMessageDto message) {
        try {
            String payload = objectMapper.writeValueAsString(message);

            rabbit.convertAndSend(
                    "used_energy",
                    payload,
                    this::setJsonContentType
            );

        } catch (Exception ex) {
            throw new IllegalStateException("Could not publish used_energy message", ex);
        }
    }

    private Message setJsonContentType(Message rabbitMessage) {
        rabbitMessage.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return rabbitMessage;
    }
}