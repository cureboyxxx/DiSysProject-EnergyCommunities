package at.uastw.UsageService.service.messaging.consumer;

import at.uastw.UsageService.dto.UsedEnergyMsgDto;
import at.uastw.UsageService.service.business.EnergyUsageService;
import at.uastw.UsageService.service.messaging.producer.CurrentPercentageProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class UsedEnergyConsumer {
    private final ObjectMapper objectMapper;
    private final EnergyUsageService energyUsageService;
    private final CurrentPercentageProducer currentPercentageProducer;

    public UsedEnergyConsumer(
            ObjectMapper objectMapper,
            EnergyUsageService energyUsageService,
            CurrentPercentageProducer currentPercentageProducer
    ) {
        this.objectMapper = objectMapper;
        this.energyUsageService = energyUsageService;
        this.currentPercentageProducer = currentPercentageProducer;
    }

    @RabbitListener(queues = "used_energy")
    public void consume(String msg) {

        System.out.println("Received used_energy: " + msg);

        try {
            UsedEnergyMsgDto dto = objectMapper.readValue(msg, UsedEnergyMsgDto.class);

            energyUsageService.handleUsedEnergy(dto);

            currentPercentageProducer.publish(dto.getDatetime());

        } catch (Exception ex) {
            throw new RuntimeException("Failed to process produced_energy message", ex);
        }
    }
}
