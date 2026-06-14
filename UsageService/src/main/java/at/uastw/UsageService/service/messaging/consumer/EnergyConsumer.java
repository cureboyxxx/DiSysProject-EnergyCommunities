package at.uastw.UsageService.service.messaging.consumer;

import at.uastw.UsageService.dto.EnergyMsgDto;
import at.uastw.UsageService.service.business.EnergyUsageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class EnergyConsumer {
    private final ObjectMapper objectMapper;
    private final EnergyUsageService energyUsageService;

    public EnergyConsumer(
            ObjectMapper objectMapper,
            EnergyUsageService energyUsageService
    ) {
        this.objectMapper = objectMapper;
        this.energyUsageService = energyUsageService;
    }

    @RabbitListener(queues = "energy_message")
    public void consume(String msg) {

        System.out.println("Received energy_message: " + msg);

        try {
            EnergyMsgDto energyMsgDto = objectMapper.readValue(msg, EnergyMsgDto.class);

            energyUsageService.handleEnergy(energyMsgDto);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to process energy message", ex);
        }
    }
}
