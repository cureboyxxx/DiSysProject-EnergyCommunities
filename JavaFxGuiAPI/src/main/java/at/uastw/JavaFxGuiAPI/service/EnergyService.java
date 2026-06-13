package at.uastw.JavaFxGuiAPI.service;

import at.uastw.JavaFxGuiAPI.dto.CurrentEnergyResponse;
import at.uastw.JavaFxGuiAPI.dto.HistoricalEnergyResponse;
import at.uastw.JavaFxGuiAPI.entity.CurrentPercentageEntity;
import at.uastw.JavaFxGuiAPI.entity.EnergyUsageEntity;
import at.uastw.JavaFxGuiAPI.repository.CurrentPercentageRepository;
import at.uastw.JavaFxGuiAPI.repository.EnergyUsageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnergyService {

    private final EnergyUsageRepository energyUsageRepository;
    private final CurrentPercentageRepository currentPercentageRepository;

    public EnergyService(EnergyUsageRepository energyUsageRepository, CurrentPercentageRepository currentPercentageRepository
    ) {
        this.energyUsageRepository = energyUsageRepository;
        this.currentPercentageRepository = currentPercentageRepository;
    }

    public CurrentEnergyResponse getCurrentEnergy() {

        CurrentPercentageEntity current =
                currentPercentageRepository.findTopByOrderByHourDesc();

        if (current == null) {
            return new CurrentEnergyResponse(null, 0.0, 0.0);
        }

        return new CurrentEnergyResponse(
                current.getHour(),
                current.getCommunityDepleted(),
                current.getGridPortion()
        );
    }

    public List<HistoricalEnergyResponse> getHistoricalEnergy(
            String start,
            String end
    ) {

        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);

        List<EnergyUsageEntity> usages =
                energyUsageRepository.findByHourBetweenOrderByHourAsc(
                        startTime,
                        endTime
                );

        List<HistoricalEnergyResponse> responses = new ArrayList<>();

        for (EnergyUsageEntity usage : usages) {

            HistoricalEnergyResponse response =
                    new HistoricalEnergyResponse(
                            usage.getHour(),
                            usage.getCommunityProduced(),
                            usage.getCommunityUsed(),
                            usage.getGridUsed()
                    );

            responses.add(response);
        }

        return responses;
    }
}