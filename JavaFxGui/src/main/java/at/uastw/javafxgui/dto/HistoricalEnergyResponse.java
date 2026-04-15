package at.uastw.javafxgui.dto;

public class HistoricalEnergyResponse {

    private String hour;
    private double communityProduced;
    private double communityUsed;
    private double gridUsed;

    public HistoricalEnergyResponse() {}

    public String getHour() { return hour; }
    public double getCommunityProduced() { return communityProduced; }
    public double getCommunityUsed() { return communityUsed; }
    public double getGridUsed() { return gridUsed; }
}