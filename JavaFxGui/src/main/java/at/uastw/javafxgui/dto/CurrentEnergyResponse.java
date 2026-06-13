package at.uastw.javafxgui.dto;

import java.time.LocalDateTime;

public class CurrentEnergyResponse {

    public LocalDateTime hour;
    public double communityDepleted;
    public double gridPortion;

    public LocalDateTime getHour() {
        return hour;
    }

    public double getCommunityDepleted() {
        return communityDepleted;
    }

    public double getGridPortion() {
        return gridPortion;
    }
}