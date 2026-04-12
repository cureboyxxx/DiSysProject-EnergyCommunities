package at.uastw.javafxgui.dto;

public class CurrentEnergyResponse {

    public String hour;
    public double communityDepleted;
    public double gridPortion;

    public String getHour() {
        return hour;
    }

    public double getCommunityDepleted() {
        return communityDepleted;
    }

    public double getGridPortion() {
        return gridPortion;
    }
}