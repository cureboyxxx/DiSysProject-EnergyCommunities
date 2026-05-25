package at.uastw.EnergyProducer.service;

import at.uastw.EnergyProducer.model.ProducedEnergyMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class ProducedEnergyRabbitMqPublisher {

    private final RabbitTemplate rabbit;
    private final ObjectMapper objectMapper;

    public ProducedEnergyRabbitMqPublisher(RabbitTemplate rabbit, ObjectMapper objectMapper) {
        this.rabbit = rabbit;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(ProducedEnergyMessage message) {
        try {
            String payload = objectMapper.writeValueAsString(message);
            rabbit.convertAndSend(
                    "produced_energy",
                    payload,
                    this::setJsonContentType
            );
        } catch (Exception exception) {
            throw new IllegalStateException("Could not publish produced energy message", exception);
        }
    }

    private Message setJsonContentType(Message rabbitMessage) {
        rabbitMessage.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return rabbitMessage;
    }
}
