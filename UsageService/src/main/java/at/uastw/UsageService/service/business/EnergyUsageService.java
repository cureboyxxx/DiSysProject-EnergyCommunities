package at.uastw.UsageService.service.business;

import at.uastw.UsageService.dto.ProducedEnergyMsgDto;
import at.uastw.UsageService.dto.UsedEnergyMsgDto;
import at.uastw.UsageService.repository.EnergyUsageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EnergyUsageService {

    private final EnergyUsageRepository repository;

    public EnergyUsageService(EnergyUsageRepository repository) {
        this.repository = repository;
    }

    /*
    @Transactional starts a database transaction around the method.
    Everything within it is either executed in full or rolled back in full.
     */
    @Transactional
    public void handleProducedEnergy(ProducedEnergyMsgDto msg) {
        LocalDateTime hour = getHourlyDateTime(msg.getDatetime());
        repository.upsertProduced(hour, msg.getAmountInKwh());
    }

    /*
    @Transactional starts a database transaction around the method.
    Everything within it is either executed in full or rolled back in full.
     */
    @Transactional
    public void handleUsedEnergy(UsedEnergyMsgDto msg) {
        LocalDateTime hour = getHourlyDateTime(msg.getDatetime());
        repository.upsertUsed(hour, msg.getAmountInKwh());
    }

    private LocalDateTime getHourlyDateTime(LocalDateTime dateTime){
        return dateTime
                .withMinute(0).withSecond(0).withNano(0);
    }
}
