package at.uastw.CurrentPercentageService.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/*
hour        | community_depleted    | grid_portion
timestamp   | double %.2f          | decimal %.2f
 */
public class CurrentPercentageDto {
    @NotNull
    private LocalDateTime hour;

    @PositiveOrZero
    private double community_depleted;

    @PositiveOrZero
    private double grid_portion;

    public CurrentPercentageDto() {
    }

    public CurrentPercentageDto(LocalDateTime hour, double community_depleted, double grid_portion) {
        setHour(hour);
        setCommunity_depleted(community_depleted);
        setGrid_portion(grid_portion);
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public double getCommunity_depleted() {
        return community_depleted;
    }

    public void setCommunity_depleted(double community_depleted) {
        this.community_depleted = community_depleted;
    }

    public double getGrid_portion() {
        return grid_portion;
    }

    public void setGrid_portion(double grid_portion) {
        this.grid_portion = grid_portion;
    }
}
