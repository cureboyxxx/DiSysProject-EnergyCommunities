package at.uastw.EnergyProducer.dto;

public class WeatherConditionDto {
    private boolean isDay;
    private int cloudCover;

    public WeatherConditionDto() {}

    public WeatherConditionDto(boolean isDay, int cloudCover) {
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
