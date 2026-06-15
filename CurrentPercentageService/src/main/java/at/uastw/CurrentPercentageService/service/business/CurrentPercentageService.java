package at.uastw.CurrentPercentageService.service.business;

import at.uastw.CurrentPercentageService.dto.CurrentPercentageMsgDto;
import at.uastw.CurrentPercentageService.entity.CurrentPercentageEntity;
import at.uastw.CurrentPercentageService.repository.CurrentPercentageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CurrentPercentageService {

    private final CurrentPercentageRepository repository;

    public CurrentPercentageService(CurrentPercentageRepository repository) {
        this.repository = repository;
    }

    public void handleCurrentPercentage(CurrentPercentageMsgDto dto) {

        try {
            validate(dto); // throws an exception when the input data is invalid
        } catch (IllegalArgumentException ex) {
            System.err.println("Invalid message: " + ex.getMessage());
            return; // Message ignorieren
        }

        LocalDateTime hour = truncateToHour(dto.getHour());
        CurrentPercentageEntity entity = loadOrCreate(hour);

        double communityProduced = dto.getCommunityProduced();
        double communityUsed = dto.getCommunityUsed();
        double gridUsed = dto.getGridUsed();
        double totalUsed = communityUsed + gridUsed;

        double communityDepleted = calculateCommunityDepleted(communityProduced, communityUsed);
        double gridPortion = calculateGridPortion(totalUsed, gridUsed);

        entity.setCommunityDepleted(communityDepleted);
        entity.setGridPortion(gridPortion);

        repository.save(entity);
    }

    // ------------------------------------------------------------
    // Business Logic
    // ------------------------------------------------------------

    private double calculateCommunityDepleted(double communityProduced, double communityUsed) {
        if (communityProduced <= 0) return 0;
        return (communityUsed / communityProduced) * 100.0;
    }

    private double calculateGridPortion(double totalUsed, double gridUsed) {
        if (totalUsed <= 0) return 0;
        return (gridUsed / totalUsed) * 100.0;
    }

    // ------------------------------------------------------------
    // Helper Methods
    // ------------------------------------------------------------

    private void validate(CurrentPercentageMsgDto dto) {
        if (dto.getHour() == null) {
            throw new IllegalArgumentException("Hour must not be null.");
        }

        if (dto.getCommunityProduced() < 0 ||
                dto.getCommunityUsed() < 0 ||
                dto.getGridUsed() < 0) {
            throw new IllegalArgumentException("Values must be non-negative.");
        }
    }

    private CurrentPercentageEntity loadOrCreate(LocalDateTime hour) {
        return repository.findById(hour)
                .orElseGet(() -> new CurrentPercentageEntity(hour, 0, 0));
    }

    private LocalDateTime truncateToHour(LocalDateTime dt) {
        return dt.withMinute(0).withSecond(0).withNano(0);
    }
}