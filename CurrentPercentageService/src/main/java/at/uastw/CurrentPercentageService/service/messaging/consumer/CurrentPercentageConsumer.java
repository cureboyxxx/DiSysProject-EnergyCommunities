package at.uastw.CurrentPercentageService.service.messaging.consumer;

import at.uastw.CurrentPercentageService.dto.CurrentPercentageMsgDto;
import at.uastw.CurrentPercentageService.service.business.CurrentPercentageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class CurrentPercentageConsumer {
    private final ObjectMapper objectMapper;
    private final CurrentPercentageService currentPercentageService;

    public CurrentPercentageConsumer(
            ObjectMapper objectMapper,
            CurrentPercentageService currentPercentageService
    ){
        this.objectMapper = objectMapper;
        this.currentPercentageService = currentPercentageService;
    }

    @RabbitListener(queues = "current_percentage")
    public void consume(String msg) {

        System.out.println("Received current_percentage: " + msg);

        try {
            CurrentPercentageMsgDto dto = objectMapper.readValue(msg, CurrentPercentageMsgDto.class);

            currentPercentageService.handleCurrentPercentage(dto);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to process produced_energy message", ex);
        }
    }
}
