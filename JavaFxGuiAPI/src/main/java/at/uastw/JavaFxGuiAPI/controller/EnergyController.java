package at.uastw.JavaFxGuiAPI.controller;

import at.uastw.JavaFxGuiAPI.dto.CurrentEnergyResponse;
import at.uastw.JavaFxGuiAPI.dto.HistoricalEnergyResponse;
import at.uastw.JavaFxGuiAPI.service.EnergyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EnergyController {

    private final EnergyService energyService;

    public EnergyController(EnergyService energyService) {
        this.energyService = energyService;
    }

    @GetMapping("/energy/current")
    public CurrentEnergyResponse getCurrentEnergy() {
        System.out.println("GET /energy/current called");
        return energyService.getCurrentEnergy();
    }

    @GetMapping("/energy/historical")
    public List<HistoricalEnergyResponse> getHistoricalEnergy(
            @RequestParam String start,
            @RequestParam String end
    ) {
        System.out.println("GET /energy/historical called: start=" + start + ", end=" + end);
        return energyService.getHistoricalEnergy(start, end);
    }
}