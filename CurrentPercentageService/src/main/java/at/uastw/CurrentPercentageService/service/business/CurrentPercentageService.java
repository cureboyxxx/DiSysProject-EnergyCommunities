package at.uastw.CurrentPercentageService.service.business;

import at.uastw.CurrentPercentageService.dto.CurrentPercentageMsgDto;
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

    /*
    @Transactional starts a database transaction around the method.
    Everything within it is either executed in full or rolled back in full.
     */
    @Transactional
    public void handleCurrentPercentage(CurrentPercentageMsgDto dto) {
        LocalDateTime hour = dto.getHour()
                .withMinute(0).withSecond(0).withNano(0);
        repository.upsertCurrentPercentage(hour);
    }
}
