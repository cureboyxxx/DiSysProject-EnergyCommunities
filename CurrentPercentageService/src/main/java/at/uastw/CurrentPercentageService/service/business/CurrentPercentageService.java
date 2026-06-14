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

    // UNTESTED!!!!
    @Transactional
    public void handleCurrentPercentage_BACKUP(CurrentPercentageMsgDto dto) {
        LocalDateTime hour = dto.getHour()
                .withMinute(0).withSecond(0).withNano(0);

        double communityProduced = dto.getCommunityProduced();
        double communityUsed = dto.getCommunityUsed();
        double gridUsed = dto.getGridUsed();
        double totalUsed = communityUsed + gridUsed;

        double communityDepleted = communityProduced == 0 ?
                0 :
                communityUsed / communityProduced * 100.0;
        double gridPortion = totalUsed == 0 ?
                0 :
                communityUsed / totalUsed * 100;

        CurrentPercentageEntity currentPercentageEntity = new CurrentPercentageEntity(
                hour,
                communityDepleted,
                gridPortion
        );

        repository.deleteAll();
        repository.save(currentPercentageEntity);
    }
    public void handleCurrentPercentage(CurrentPercentageMsgDto dto) {
        LocalDateTime hour = dto.getHour()
                .withMinute(0).withSecond(0).withNano(0);

        repository.upsertCurrentPercentage(hour, dto.getCommunityProduced(), dto.getCommunityUsed(), dto.getGridUsed());
    }
}