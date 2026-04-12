package at.uastw.EnergyProducer.model;

public class WeatherCondition {
    private boolean isDay;
    private int cloudCover;

    public WeatherCondition() {}

    public WeatherCondition(boolean isDay, int cloudCover) {
        this.isDay = isDay;
        this.cloudCover = cloudCover;
    }

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        isDay = day;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }
}
