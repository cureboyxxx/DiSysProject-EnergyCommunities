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

        // Mock data for 2026-04-01 for 24h in 15min intervals
        // Mock energy community with about 10 single family homes equipped with solar panels
        List<HistoricalEnergyResponse> allHistoricalEnergyResponses = List.of(
                new HistoricalEnergyResponse("2026-04-01T00:00:00", 0.00, 0.22, 0.22),
                new HistoricalEnergyResponse("2026-04-01T00:15:00", 0.00, 0.20, 0.20),
                new HistoricalEnergyResponse("2026-04-01T00:30:00", 0.00, 0.18, 0.18),
                new HistoricalEnergyResponse("2026-04-01T00:45:00", 0.00, 0.17, 0.17),
                new HistoricalEnergyResponse("2026-04-01T01:00:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T01:15:00", 0.00, 0.15, 0.15),
                new HistoricalEnergyResponse("2026-04-01T01:30:00", 0.00, 0.15, 0.15),
                new HistoricalEnergyResponse("2026-04-01T01:45:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T02:00:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T02:15:00", 0.00, 0.15, 0.15),
                new HistoricalEnergyResponse("2026-04-01T02:30:00", 0.00, 0.15, 0.15),
                new HistoricalEnergyResponse("2026-04-01T02:45:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T03:00:00", 0.00, 0.18, 0.18),
                new HistoricalEnergyResponse("2026-04-01T03:15:00", 0.00, 0.17, 0.17),
                new HistoricalEnergyResponse("2026-04-01T03:30:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T03:45:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T04:00:00", 0.00, 0.18, 0.18),
                new HistoricalEnergyResponse("2026-04-01T04:15:00", 0.00, 0.17, 0.17),
                new HistoricalEnergyResponse("2026-04-01T04:30:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T04:45:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T05:00:00", 0.00, 0.18, 0.18),
                new HistoricalEnergyResponse("2026-04-01T05:15:00", 0.00, 0.18, 0.18),
                new HistoricalEnergyResponse("2026-04-01T05:30:00", 0.00, 0.19, 0.19),
                new HistoricalEnergyResponse("2026-04-01T05:45:00", 0.00, 0.20, 0.20),
                new HistoricalEnergyResponse("2026-04-01T06:00:00", 0.00, 0.24, 0.24),
                new HistoricalEnergyResponse("2026-04-01T06:15:00", 0.02, 0.28, 0.26),
                new HistoricalEnergyResponse("2026-04-01T06:30:00", 0.05, 0.34, 0.29),
                new HistoricalEnergyResponse("2026-04-01T06:45:00", 0.10, 0.40, 0.30),
                new HistoricalEnergyResponse("2026-04-01T07:00:00", 0.18, 0.48, 0.30),
                new HistoricalEnergyResponse("2026-04-01T07:15:00", 0.30, 0.55, 0.25),
                new HistoricalEnergyResponse("2026-04-01T07:30:00", 0.45, 0.62, 0.17),
                new HistoricalEnergyResponse("2026-04-01T07:45:00", 0.65, 0.70, 0.05),
                new HistoricalEnergyResponse("2026-04-01T08:00:00", 0.90, 0.76, 0.00),
                new HistoricalEnergyResponse("2026-04-01T08:15:00", 1.20, 0.82, 0.00),
                new HistoricalEnergyResponse("2026-04-01T08:30:00", 1.55, 0.88, 0.00),
                new HistoricalEnergyResponse("2026-04-01T08:45:00", 1.90, 0.92, 0.00),
                new HistoricalEnergyResponse("2026-04-01T09:00:00", 2.25, 0.86, 0.00),
                new HistoricalEnergyResponse("2026-04-01T09:15:00", 2.60, 0.80, 0.00),
                new HistoricalEnergyResponse("2026-04-01T09:30:00", 2.95, 0.74, 0.00),
                new HistoricalEnergyResponse("2026-04-01T09:45:00", 3.25, 0.68, 0.00),
                new HistoricalEnergyResponse("2026-04-01T10:00:00", 3.50, 0.62, 0.00),
                new HistoricalEnergyResponse("2026-04-01T10:15:00", 3.75, 0.58, 0.00),
                new HistoricalEnergyResponse("2026-04-01T10:30:00", 3.95, 0.55, 0.00),
                new HistoricalEnergyResponse("2026-04-01T10:45:00", 4.10, 0.52, 0.00),
                new HistoricalEnergyResponse("2026-04-01T11:00:00", 4.20, 0.50, 0.00),
                new HistoricalEnergyResponse("2026-04-01T11:15:00", 4.28, 0.48, 0.00),
                new HistoricalEnergyResponse("2026-04-01T11:30:00", 4.32, 0.47, 0.00),
                new HistoricalEnergyResponse("2026-04-01T11:45:00", 4.35, 0.46, 0.00),
                new HistoricalEnergyResponse("2026-04-01T12:00:00", 4.32, 0.45, 0.00),
                new HistoricalEnergyResponse("2026-04-01T12:15:00", 4.25, 0.44, 0.00),
                new HistoricalEnergyResponse("2026-04-01T12:30:00", 4.15, 0.44, 0.00),
                new HistoricalEnergyResponse("2026-04-01T12:45:00", 4.00, 0.43, 0.00),
                new HistoricalEnergyResponse("2026-04-01T13:00:00", 3.80, 0.44, 0.00),
                new HistoricalEnergyResponse("2026-04-01T13:15:00", 3.55, 0.45, 0.00),
                new HistoricalEnergyResponse("2026-04-01T13:30:00", 3.25, 0.46, 0.00),
                new HistoricalEnergyResponse("2026-04-01T13:45:00", 2.90, 0.48, 0.00),
                new HistoricalEnergyResponse("2026-04-01T14:00:00", 2.50, 0.50, 0.00),
                new HistoricalEnergyResponse("2026-04-01T14:15:00", 2.10, 0.54, 0.00),
                new HistoricalEnergyResponse("2026-04-01T14:30:00", 1.75, 0.58, 0.00),
                new HistoricalEnergyResponse("2026-04-01T14:45:00", 1.40, 0.64, 0.00),
                new HistoricalEnergyResponse("2026-04-01T15:00:00", 1.10, 0.72, 0.00),
                new HistoricalEnergyResponse("2026-04-01T15:15:00", 0.82, 0.80, 0.00),
                new HistoricalEnergyResponse("2026-04-01T15:30:00", 0.58, 0.88, 0.30),
                new HistoricalEnergyResponse("2026-04-01T15:45:00", 0.38, 0.96, 0.58),
                new HistoricalEnergyResponse("2026-04-01T16:00:00", 0.24, 1.02, 0.78),
                new HistoricalEnergyResponse("2026-04-01T16:15:00", 0.14, 1.08, 0.94),
                new HistoricalEnergyResponse("2026-04-01T16:30:00", 0.08, 1.12, 1.04),
                new HistoricalEnergyResponse("2026-04-01T16:45:00", 0.04, 1.15, 1.11),
                new HistoricalEnergyResponse("2026-04-01T17:00:00", 0.02, 1.10, 1.08),
                new HistoricalEnergyResponse("2026-04-01T17:15:00", 0.00, 1.04, 1.04),
                new HistoricalEnergyResponse("2026-04-01T17:30:00", 0.00, 0.98, 0.98),
                new HistoricalEnergyResponse("2026-04-01T17:45:00", 0.00, 0.92, 0.92),
                new HistoricalEnergyResponse("2026-04-01T18:00:00", 0.00, 0.86, 0.86),
                new HistoricalEnergyResponse("2026-04-01T18:15:00", 0.00, 0.80, 0.80),
                new HistoricalEnergyResponse("2026-04-01T18:30:00", 0.00, 0.74, 0.74),
                new HistoricalEnergyResponse("2026-04-01T18:45:00", 0.00, 0.68, 0.68),
                new HistoricalEnergyResponse("2026-04-01T19:00:00", 0.00, 0.60, 0.60),
                new HistoricalEnergyResponse("2026-04-01T19:15:00", 0.00, 0.52, 0.52),
                new HistoricalEnergyResponse("2026-04-01T19:30:00", 0.00, 0.45, 0.45),
                new HistoricalEnergyResponse("2026-04-01T19:45:00", 0.00, 0.38, 0.38),
                new HistoricalEnergyResponse("2026-04-01T20:00:00", 0.00, 0.34, 0.34),
                new HistoricalEnergyResponse("2026-04-01T20:15:00", 0.00, 0.31, 0.31),
                new HistoricalEnergyResponse("2026-04-01T20:30:00", 0.00, 0.28, 0.28),
                new HistoricalEnergyResponse("2026-04-01T20:45:00", 0.00, 0.26, 0.26),
                new HistoricalEnergyResponse("2026-04-01T21:00:00", 0.00, 0.24, 0.24),
                new HistoricalEnergyResponse("2026-04-01T21:15:00", 0.00, 0.22, 0.22),
                new HistoricalEnergyResponse("2026-04-01T21:30:00", 0.00, 0.20, 0.20),
                new HistoricalEnergyResponse("2026-04-01T21:45:00", 0.00, 0.19, 0.19),
                new HistoricalEnergyResponse("2026-04-01T22:00:00", 0.00, 0.18, 0.18),
                new HistoricalEnergyResponse("2026-04-01T22:15:00", 0.00, 0.17, 0.17),
                new HistoricalEnergyResponse("2026-04-01T22:30:00", 0.00, 0.17, 0.17),
                new HistoricalEnergyResponse("2026-04-01T22:45:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T23:00:00", 0.00, 0.16, 0.16),
                new HistoricalEnergyResponse("2026-04-01T23:15:00", 0.00, 0.15, 0.15),
                new HistoricalEnergyResponse("2026-04-01T23:30:00", 0.00, 0.15, 0.15),
                new HistoricalEnergyResponse("2026-04-01T23:45:00", 0.00, 0.14, 0.14)
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