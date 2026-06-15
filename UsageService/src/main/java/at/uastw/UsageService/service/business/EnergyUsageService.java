package at.uastw.UsageService.service.business;

import at.uastw.UsageService.dto.CurrentPercentageMsgDto;
import at.uastw.UsageService.dto.EnergyMsgDto;
import at.uastw.UsageService.entity.EnergyUsageEntity;
import at.uastw.UsageService.repository.EnergyUsageRepository;
import at.uastw.UsageService.service.messaging.producer.CurrentPercentageProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EnergyUsageService {
    private final EnergyUsageRepository repository;
    private final CurrentPercentageProducer currentPercentageProducer;

    public EnergyUsageService(
            EnergyUsageRepository repository,
            CurrentPercentageProducer currentPercentageProducer
    ) {
        this.repository = repository;
        this.currentPercentageProducer = currentPercentageProducer;
    }

    public void handleEnergy(EnergyMsgDto dto) {

        try {
            validate(dto); // throws an exception when the input data is invalid
        } catch (IllegalArgumentException ex) {
            System.err.println("Invalid message: " + ex.getMessage());
            return; // Message ignorieren
        }

        LocalDateTime hour = truncateToHour(dto.getDatetime());
        EnergyUsageEntity entity = loadOrCreate(hour);

        switch (dto.getType()) {
            case "PRODUCER" -> applyProducedEnergy(entity, dto.getAmountInKwh());
            case "USER"     -> applyUsedEnergy(entity, dto.getAmountInKwh());
        }

        repository.save(entity);

        publishCurrentPercentage(entity);
    }

    // ------------------------------------------------------------
    // Business Logic
    // ------------------------------------------------------------

    private void applyProducedEnergy(EnergyUsageEntity entity, double amount) {
        entity.setCommunityProduced(entity.getCommunityProduced() + amount);
    }

    private void applyUsedEnergy(EnergyUsageEntity entity, double amount) {
        double proposedCommunityUsed = entity.getCommunityUsed() + amount;

        double communityUsed = Math.min(proposedCommunityUsed, entity.getCommunityProduced());
        double gridUsed = Math.max(
                entity.getGridUsed(),
                (proposedCommunityUsed + entity.getGridUsed()) - entity.getCommunityProduced()
        );

        entity.setCommunityUsed(communityUsed);
        entity.setGridUsed(gridUsed);
    }

    // ------------------------------------------------------------
    // Helper Methods
    // ------------------------------------------------------------

    private void validate(EnergyMsgDto dto) {

        if (dto.getAmountInKwh() < 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }

        if (!dto.getType().equals("PRODUCER") && !dto.getType().equals("USER")) {
            throw new IllegalArgumentException("Unknown energy type: " + dto.getType());
        }
    }

    private EnergyUsageEntity loadOrCreate(LocalDateTime hour) {
        return repository.findById(hour)
                .orElseGet(() -> new EnergyUsageEntity(hour, 0, 0, 0));
    }

    private LocalDateTime truncateToHour(LocalDateTime dt) {
        return dt.withMinute(0).withSecond(0).withNano(0);
    }

    private void publishCurrentPercentage(EnergyUsageEntity entity) {
        CurrentPercentageMsgDto msg = new CurrentPercentageMsgDto(
                entity.getHour(),
                entity.getCommunityProduced(),
                entity.getCommunityUsed(),
                entity.getGridUsed()
        );
        currentPercentageProducer.publish(msg);
    }
}
