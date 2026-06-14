package at.uastw.UsageService.service.messaging.producer;

import at.uastw.UsageService.dto.CurrentPercentageMsgDto;
import at.uastw.UsageService.entity.EnergyUsageEntity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class CurrentPercentageProducer {

    private final RabbitTemplate rabbit;
    private final ObjectMapper objectMapper;

    public CurrentPercentageProducer(RabbitTemplate rabbit, ObjectMapper objectMapper) {
        this.rabbit = rabbit;
        this.objectMapper = objectMapper;
    }

    public void publish(CurrentPercentageMsgDto currentPercentageMsgDto) {
        try {
            String payload = objectMapper.writeValueAsString(currentPercentageMsgDto);

            rabbit.convertAndSend("current_percentage", payload);

        } catch (Exception ex) {
            throw new IllegalStateException("Could not publish current_percentage message", ex);
        }
    }
}
