package at.uastw.EnergyUser.service;

import at.uastw.EnergyUser.model.UsedEnergyMessage;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class UsedEnergyRabbitMqPublisher {

    private final RabbitTemplate rabbit;
    private final ObjectMapper objectMapper;

    public UsedEnergyRabbitMqPublisher(RabbitTemplate rabbit, ObjectMapper objectMapper) {
        this.rabbit = rabbit;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(UsedEnergyMessage message) {
        try {
            String payload = objectMapper.writeValueAsString(message);
            rabbit.convertAndSend(
                    "used_energy",
                    payload,
                    rabbitMessage -> {
                        rabbitMessage.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
                        return rabbitMessage;
                    }
            );
        } catch (Exception exception) {
            throw new IllegalStateException("Could not publish used energy message", exception);
        }
    }
}
