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

    public void handleEnergy(EnergyMsgDto energyMsgDto) {
        EnergyUsageEntity energyUsageEntity = null;
        LocalDateTime hour = getHourlyDateTime(energyMsgDto.getDatetime());

        switch (energyMsgDto.getType()){
            case "PRODUCER":
                energyUsageEntity = repository.upsertProduced(hour, energyMsgDto.getAmountInKwh());
                break;
            case "USER":
                energyUsageEntity = repository.upsertUsed(hour, energyMsgDto.getAmountInKwh());
                break;
            default:
                System.out.println("Failed to process energy message: undefined type");
                return;
        }

        CurrentPercentageMsgDto currentPercentageMsgDto = new CurrentPercentageMsgDto(
                energyUsageEntity.getHour(),
                energyUsageEntity.getCommunityProduced(),
                energyUsageEntity.getCommunityUsed(),
                energyUsageEntity.getGridUsed()
        );
        currentPercentageProducer.publish(currentPercentageMsgDto);
    }

    private LocalDateTime getHourlyDateTime(LocalDateTime dateTime){
        return dateTime
                .withMinute(0).withSecond(0).withNano(0);
    }
}
