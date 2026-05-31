package at.uastw.UsageService.service.messaging.producer;

import at.uastw.UsageService.dto.CurrentPercentageMsgDto;
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

    public void publish(LocalDateTime datetime) {
        try {
            CurrentPercentageMsgDto dto = new CurrentPercentageMsgDto();
            dto.setDatetime(datetime.withMinute(0).withSecond(0).withNano(0));

            String payload = objectMapper.writeValueAsString(dto);

            rabbit.convertAndSend(
                    "current_percentage",
                    payload,
                    this::setJsonContentType
            );

        } catch (Exception ex) {
            throw new IllegalStateException("Could not publish current_percentage message", ex);
        }
    }

    private Message setJsonContentType(Message rabbitMessage) {
        rabbitMessage.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
        return rabbitMessage;
    }
}
