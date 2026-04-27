package at.uastw.JavaFxGuiAPI.service;

import at.uastw.JavaFxGuiAPI.dto.CurrentEnergyResponse;
import at.uastw.JavaFxGuiAPI.dto.HistoricalEnergyResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyService {

    public CurrentEnergyResponse getCurrentEnergy() {
        return new CurrentEnergyResponse(
                "2026-04-01T14:00:00",
                86.0,
                7.38
        );
    }

    public List<HistoricalEnergyResponse> getHistoricalEnergy(String start, String end) {

        // Mock data for 2026-04-01
        // Mock energy community with about 10 single family homes equipped with solar panels
        List<HistoricalEnergyResponse> allHistoricalEnergyResponses = List.of(
                new HistoricalEnergyResponse("2026-04-01T00:00", 0.0, 6.8, 6.8),
                new HistoricalEnergyResponse("2026-04-01T01:00", 0.0, 5.9, 5.9),
                new HistoricalEnergyResponse("2026-04-01T02:00", 0.0, 5.4, 5.4),
                new HistoricalEnergyResponse("2026-04-01T03:00", 0.0, 5.1, 5.1),
                new HistoricalEnergyResponse("2026-04-01T04:00", 0.0, 5.3, 5.3),
                new HistoricalEnergyResponse("2026-04-01T05:00", 0.2, 6.7, 6.5),

                new HistoricalEnergyResponse("2026-04-01T06:00", 1.1, 10.8, 9.7),
                new HistoricalEnergyResponse("2026-04-01T07:00", 3.8, 15.6, 11.8),
                new HistoricalEnergyResponse("2026-04-01T08:00", 8.9, 13.9, 5.0),
                new HistoricalEnergyResponse("2026-04-01T09:00", 16.7, 11.2, 0.0),
                new HistoricalEnergyResponse("2026-04-01T10:00", 25.4, 10.4, 0.0),
                new HistoricalEnergyResponse("2026-04-01T11:00", 34.8, 10.1, 0.0),

                new HistoricalEnergyResponse("2026-04-01T12:00", 41.6, 11.7, 0.0),
                new HistoricalEnergyResponse("2026-04-01T13:00", 44.2, 10.9, 0.0),
                new HistoricalEnergyResponse("2026-04-01T14:00", 39.7, 10.6, 0.0),
                new HistoricalEnergyResponse("2026-04-01T15:00", 31.5, 11.8, 0.0),
                new HistoricalEnergyResponse("2026-04-01T16:00", 21.9, 14.2, 0.0),
                new HistoricalEnergyResponse("2026-04-01T17:00", 12.4, 18.9, 6.5),

                new HistoricalEnergyResponse("2026-04-01T18:00", 5.1, 24.8, 19.7),
                new HistoricalEnergyResponse("2026-04-01T19:00", 1.3, 27.6, 26.3),
                new HistoricalEnergyResponse("2026-04-01T20:00", 0.0, 22.4, 22.4),
                new HistoricalEnergyResponse("2026-04-01T21:00", 0.0, 17.9, 17.9),
                new HistoricalEnergyResponse("2026-04-01T22:00", 0.0, 12.1, 12.1),
                new HistoricalEnergyResponse("2026-04-01T23:00", 0.0, 8.3, 8.3)
        );

        List<HistoricalEnergyResponse> filteredHistoricalEnergyResponses = allHistoricalEnergyResponses.stream()
                .filter(e -> e.getHour().compareTo(start) >= 0)
                .filter(e -> e.getHour().compareTo(end) <= 0)
                .toList();

        if (filteredHistoricalEnergyResponses.isEmpty()) {
            return List.of();
        }

        double totalCommunityProduced = filteredHistoricalEnergyResponses.stream()
                .mapToDouble(HistoricalEnergyResponse::getCommunityProduced)
                .sum();

        double totalCommunityUsed = filteredHistoricalEnergyResponses.stream()
                .mapToDouble(HistoricalEnergyResponse::getCommunityUsed)
                .sum();

        double totalGridUsed = filteredHistoricalEnergyResponses.stream()
                .mapToDouble(HistoricalEnergyResponse::getGridUsed)
                .sum();

        return List.of(new HistoricalEnergyResponse(
                start,
                totalCommunityProduced,
                totalCommunityUsed,
                totalGridUsed
        ));
    }
}