package at.uastw.javafxgui.dto;

import java.time.LocalDateTime;

public class HistoricalEnergyResponse {

    private LocalDateTime hour;
    private double communityProduced;
    private double communityUsed;
    private double gridUsed;

    public HistoricalEnergyResponse() {}

    public LocalDateTime getHour() { return hour; }
    public double getCommunityProduced() { return communityProduced; }
    public double getCommunityUsed() { return communityUsed; }
    public double getGridUsed() { return gridUsed; }
}