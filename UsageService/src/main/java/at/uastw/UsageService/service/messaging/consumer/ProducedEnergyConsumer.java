package at.uastw.UsageService.service.messaging.consumer;

import at.uastw.UsageService.dto.ProducedEnergyMsgDto;
import at.uastw.UsageService.service.business.EnergyUsageService;
import at.uastw.UsageService.service.messaging.producer.CurrentPercentageProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;


@Service
public class ProducedEnergyConsumer {

    private final ObjectMapper objectMapper;
    private final EnergyUsageService energyUsageService;
    private final CurrentPercentageProducer currentPercentageProducer;

    public ProducedEnergyConsumer(
            ObjectMapper objectMapper,
            EnergyUsageService energyUsageService,
            CurrentPercentageProducer currentPercentageProducer
    ) {
        this.objectMapper = objectMapper;
        this.energyUsageService = energyUsageService;
        this.currentPercentageProducer = currentPercentageProducer;
    }

    @RabbitListener(queues = "produced_energy")
    public void consume(String msg) {

        System.out.println("Received produced_energy: " + msg);

        try {
            ProducedEnergyMsgDto dto = objectMapper.readValue(msg, ProducedEnergyMsgDto.class);

            energyUsageService.handleProducedEnergy(dto);

            currentPercentageProducer.publish(dto.getDatetime());

        } catch (Exception ex) {
            throw new RuntimeException("Failed to process produced_energy message", ex);
        }
    }
}

