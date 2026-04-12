package at.uastw.EnergyProducer.service.impl;

// This is a mock implementation that
// just prints the energy produced message to the console
// to simulate the rabbitMQ payload
// while we do not have the actual rabbitMQ implementation yet

import at.uastw.EnergyProducer.model.ProducedEnergyMessage;
import at.uastw.EnergyProducer.service.MessagePublisher;
import org.springframework.stereotype.Service;

@Service
public class MockRabbitMqPublisher implements MessagePublisher {

    public void publishMessage(ProducedEnergyMessage message) {
        String payload = String.format(
                "type: %s\nassociation: %s\nkwh: %.5f\ndatetime: %s",
                message.getType(),
                message.getAssociation(),
                message.getAmountInKwh(),
                message.getDatetime()
        );

    System.out.println("--- Prepared RabbitMQ Message ---");
    System.out.println(payload);
    System.out.println("---------------------------------");

    }
}
