package at.uastw.JavaFxGuiAPI.service;

import at.uastw.JavaFxGuiAPI.dto.CurrentEnergyResponse;
import at.uastw.JavaFxGuiAPI.dto.HistoricalEnergyResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EnergyService {

    public CurrentEnergyResponse getCurrentEnergy() {
        return new CurrentEnergyResponse(
                "2025-01-10T14:00:00",
                100.0,
                5.63
        );
    }

    public List<HistoricalEnergyResponse> getHistoricalEnergy(String start, String end) {

        List<HistoricalEnergyResponse> allData = List.of(
                new HistoricalEnergyResponse("2025-01-10T10:00:00", 12.0, 10.5, 1.2),
                new HistoricalEnergyResponse("2025-01-10T11:00:00", 14.2, 13.8, 0.9),
                new HistoricalEnergyResponse("2025-01-10T12:00:00", 16.5, 15.0, 1.5),
                new HistoricalEnergyResponse("2025-01-10T13:00:00", 15.015, 14.033, 2.049),
                new HistoricalEnergyResponse("2025-01-10T14:00:00", 18.05, 18.05, 1.076),
                new HistoricalEnergyResponse("2025-01-10T15:00:00", 20.0, 17.0, 0.5),
                new HistoricalEnergyResponse("2025-01-10T16:00:00", 19.5, 18.0, 0.8)
        );

        return allData.stream()
                .filter(e -> e.getHour().compareTo(start) >= 0)
                .filter(e -> e.getHour().compareTo(end) <= 0)
                .sorted(Comparator.comparing(HistoricalEnergyResponse::getHour))
                .toList();
    }
}