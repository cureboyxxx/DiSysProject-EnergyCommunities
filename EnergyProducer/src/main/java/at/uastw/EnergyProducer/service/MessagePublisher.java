package at.uastw.EnergyProducer.service;

import at.uastw.EnergyProducer.model.ProducedEnergyMessage;

public interface MessagePublisher {
    void publishMessage(ProducedEnergyMessage message);
}
