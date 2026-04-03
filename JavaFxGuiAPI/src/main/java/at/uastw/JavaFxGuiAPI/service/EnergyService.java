package at.uastw.JavaFxGuiAPI.service;

import at.uastw.JavaFxGuiAPI.dto.CurrentEnergyResponse;
import at.uastw.JavaFxGuiAPI.dto.HistoricalEnergyResponse;
import org.springframework.stereotype.Service;

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
        return List.of(
                new HistoricalEnergyResponse("2025-01-10T13:00:00", 15.015, 14.033, 2.049),
                new HistoricalEnergyResponse("2025-01-10T14:00:00", 18.050, 18.050, 1.076)
        );
    }
}